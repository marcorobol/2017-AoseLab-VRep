package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import coppelia.remoteApi;
import unitn.aose.warehousesim.adapter.vrep.AdapterVRep;
import unitn.aose.warehousesim.adapter.vrep.ConfiguratorVRep;
import unitn.aose.warehousesim.agent.AgentGui;
import unitn.aose.warehousesim.agent.IRobotAgent;
import unitn.aose.warehousesim.agent.IWarehouseAgent;
import unitn.aose.warehousesim.agent.RobotAgentFactory;
import unitn.aose.warehousesim.agent.RobotAgentGui;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.configuration.ConfigurationOne;
import unitn.aose.warehousesim.configuration.ConfigurationThree;
import unitn.aose.warehousesim.configuration.IConfigurator;
import unitn.aose.warehousesim.simulator.AdapterSyncronousTriggeringCycle;
import unitn.aose.warehousesim.simulator.AdapterUpdateCycle;
import unitn.aose.warehousesim.simulator.SimulationGui;
import unitn.aose.warehousesim.simulator.Warehouse;
import unitn.aose.warehousesim.tellerMachine.TellerMachineGui;
import bsh.Interpreter;

public class Launcher {

	/**
	 * the beanshell script file that will be executed on startup
	 */
	public static final String SCRIPT_SOURCEFILE = "rootScript.bsh";

	/**
	 * the beanshell variable used to map the warehouse
	 */
	public static final String VAR_WAREHOUSE = "warehouse";

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
			IRobot r = warehouse.getRobot(c);
			IRobotAgent ra = factory.createAgent(r);
			if (null == ra) {
				System.out.println("ERROR no agent created for robot " + r.getName());
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

	private static IWarehouseAgent getCoordinatorAgent(RobotAgentFactory factory, IWarehouse warehouse) {
		IWarehouseAgent wa = factory.createAgent(warehouse);
		if (null == wa) {
			System.out.println("ERROR no agent created for warehouse " + warehouse);
		} else {
			// XXX: initialize?
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
		IWarehouseAgent coordinator = getCoordinatorAgent(caFactory, warehouse);

		// XXX: I just want to use a single agent at this time for testing
		// FIXME: remove this code to restore normal behaviour
		Collection<IRobotAgent> tempAgentsList = new LinkedList<IRobotAgent>();
		for (IRobotAgent a : agentsList) {
			if (a.getRobot().getName().equals("RobotMotorA1")) {
				tempAgentsList.add(a);
				break;
			}
		}
		coordinator.coordinate(agentsList);

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
					i.set(VAR_WAREHOUSE, warehouse);
					i.source(SCRIPT_SOURCEFILE);
				} catch (Exception e) {
					System.out.println("ERROR: " + e);
				}
			}
		};
		beanShellThread.start();

		/*
		 * TellerMachines
		 */
		List<ITellerMachine> machineList = new ArrayList<ITellerMachine>();
		for (DepositWithdrawAreaRef a : warehouse.getDepositWithdrawAreas()) {
			machineList.add(warehouse.getTellerMachine(a));
		}
		new TellerMachineGui(machineList);

		/*
		 * SimulationGui
		 */
		SimulationGui simulationGui = new SimulationGui(adapter, warehouse);

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
