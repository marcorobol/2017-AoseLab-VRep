package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import unitn.aose.warehousesim.adapter.vrep.AdapterVRep;
import unitn.aose.warehousesim.adapter.vrep.ConfiguratorVRep;
import unitn.aose.warehousesim.agent.IRobotAgent;
import unitn.aose.warehousesim.agent.IWarehouseAgent;
import unitn.aose.warehousesim.agent.RobotAgentFactory;
import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.Logger;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.configuration.ConfigurationThree;
import unitn.aose.warehousesim.configuration.IConfigurator;
import unitn.aose.warehousesim.gui.AgentGui;
import unitn.aose.warehousesim.gui.RobotAgentGui;
import unitn.aose.warehousesim.gui.TellerMachineGui;
import unitn.aose.warehousesim.simulator.AdapterSyncronousTriggeringCycle;
import unitn.aose.warehousesim.simulator.AdapterUpdateCycle;
import unitn.aose.warehousesim.simulator.SimulationGui;
import unitn.aose.warehousesim.simulator.Warehouse;
import bsh.Interpreter;
import coppelia.remoteApi;

public class Launcher {

	/**
	 * the beanshell script file that will be executed on startup
	 */
	public static final String SCRIPT_SOURCEFILE = "rootScript.bsh";

	/**
	 * the beanshell variable used to map the warehouse
	 */
	public static final String VAR_WAREHOUSE = "warehouse", VAR_TICKETMANAGER = "ticketManager";

	protected static final String VAR_COORDINATOR = "coordinator";

	public static final String CLASS_ROBOTAGENT = "unitn.aose.warehousesim.agent.RobotController",
			CLASS_WAREHOUSEAGENT = "unitn.aose.warehousesim.agent.RobotCoordinator";

	/**
	 * Using the RobotAgentFactory create an instance of IRobotAgent using the
	 * environment variable "wdas.factory.agent.class" for each robot currently
	 * available in the given environment.
	 * 
	 * @param warehouse
	 *            the current environment to get the robots from
	 * @return a list with all the created agents
	 */
	private static Collection<IRobotAgent> getRobotAgents(RobotAgentFactory factory, IWarehouse warehouse) {
		final Collection<IRobotAgent> raList = new LinkedList<IRobotAgent>();
		for (CartRef c : warehouse.getCarts()) {
			final IRobot r = warehouse.getRobot(c);
			final IRobotAgent ra = factory.createAgent(r);
			if (null == ra) {
				Logger.err.println("no agent created for robot " + r.getName());
			} else {
				raList.add(ra);
				r.getSimulationTime().registerListener(new IListener<Long>() {
					public void notifyChanged(Long value) {
						ra.updateTime(value);
					}
				});
			}
		}
		// TODO: return the agent and send out the first goal
		return raList;
	}

	private static IWarehouseAgent getCoordinatorAgent(RobotAgentFactory factory, IWarehouse warehouse, Collection<IRobotAgent> agentsList) {
		IWarehouseAgent wa = factory.createAgent(warehouse);
		if (null == wa) {
			Logger.err.println("no agent created for warehouse " + warehouse);
		} else {
			for (final DepositWithdrawAreaRef a : warehouse.getDepositWithdrawAreas()) {
				ITellerMachine tm = warehouse.getTellerMachine(a);
				tm.getState().registerListener(new IListener<AreaState>() {
					public void notifyChanged(AreaState value) {
						if(tm.getState().get()==AreaState.elaboratingDeposit || tm.getState().get()==AreaState.elaboratingWithdraw)
							if(tm.getGeneratedTicket()!=null)
								wa.handleRequest(
									tm.getGeneratedTicket().getCode(),
									tm.getState().get()==AreaState.elaboratingDeposit,
									a.getName(),
									tm.getGeneratedTicket().getBox().getName());
					}
				});
			}
		}
		return wa;
	}

	public static void main(String[] args) throws InterruptedException {

		/*
		 * Adapter
		 */
		remoteApi vrep = new remoteApi();
		int clientID = vrep.simxStart("127.0.0.1", 19997, true, true, 5000, 5);
		AdapterVRep adapter = new AdapterVRep(vrep, clientID);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				adapter.stop();
			}
		});

		/*
		 * Warehouse
		 */
		Warehouse warehouse = new Warehouse(adapter);

		/*
		 * Configuration
		 */
		IConfigurator configuratorVRep = new ConfiguratorVRep(adapter, warehouse);
		ConfigurationThree conf = new ConfigurationThree(configuratorVRep);
		adapter.play();
		conf.initialize(warehouse);

		/*
		 * Sezione per il caricamento degli agenti
		 */
		RobotAgentFactory caFactory = new RobotAgentFactory(CLASS_ROBOTAGENT, CLASS_WAREHOUSEAGENT);
		Collection<IRobotAgent> agentsList = getRobotAgents(caFactory, warehouse);
		IWarehouseAgent coordinator = getCoordinatorAgent(caFactory, warehouse, agentsList);
		coordinator.coordinate(agentsList);

//		// XXX: I just want to use a single agent at this time for testing
//		// FIXME: remove this code to restore normal behaviour
//		Collection<IRobotAgent> tempAgentsList = new LinkedList<IRobotAgent>();
//		for (IRobotAgent a : agentsList) {
//			if (a.getRobot().getName().equals("RobotMotorA1")) {
//				tempAgentsList.add(a);
//				break;
//			}
//		}

		/*
		 * Agents Gui
		 */
		List<IRobot> robotList = new ArrayList<IRobot>();
		for (CartRef c : warehouse.getCarts())
			robotList.add(warehouse.getRobot(c));
		// new Thread(new AgentJava(robotList)).start();
		new AgentGui(robotList);
		new RobotAgentGui(agentsList);

		/*
		 * Beanshell
		 */
		Thread beanShellThread = new Thread("beanshell") {
			public void run() {
				Interpreter i = new Interpreter();
				try {
					for (IRobotAgent robot : agentsList) { // foreach robot
						i.set(robot.getRobot().getName(), robot);
					}
					for (DepositWithdrawAreaRef area : warehouse.getDepositWithdrawAreas()) { // foreach robot
						ITellerMachine tellerMachine = warehouse.getTellerMachine(area);
						i.set(tellerMachine.getName(), tellerMachine);
					}
					i.set(VAR_WAREHOUSE, warehouse);
					i.set(VAR_COORDINATOR, coordinator);
					i.set(VAR_TICKETMANAGER, warehouse.getTicketManager());
					i.source(SCRIPT_SOURCEFILE);
				} catch (Exception e) {
					Logger.err.println("ERROR: " + e);
				}
			}
		};
		beanShellThread.start();

		/*
		 * TellerMachinesGui
		 */
		List<ITellerMachine> machineList = new ArrayList<ITellerMachine>();
		for (DepositWithdrawAreaRef a : warehouse.getDepositWithdrawAreas()) {
			machineList.add(warehouse.getTellerMachine(a));
		}
		new TellerMachineGui(machineList, warehouse);

		/*
		 * SimulationGui
		 */
		new SimulationGui(adapter, warehouse);

		/*
		 * Update and triggering cycles
		 */

		Thread aucThread = new Thread(new AdapterUpdateCycle(adapter, warehouse));
		aucThread.setName("AdapterUpdateCycle");
		aucThread.start();

		Thread astcThread = new Thread(new AdapterSyncronousTriggeringCycle(adapter, warehouse));
		astcThread.setName("AdapterSyncronousTriggeringCycle");
		astcThread.start();
	}

}
