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
import unitn.aose.warehousesim.agent.RobotAgentFactory;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.configuration.ConfigurationOne;
import unitn.aose.warehousesim.configuration.IConfigurator;
import unitn.aose.warehousesim.simulator.AdapterSyncronousTriggeringCycle;
import unitn.aose.warehousesim.simulator.AdapterUpdateCycle;
import unitn.aose.warehousesim.simulator.IAdapter;
import unitn.aose.warehousesim.simulator.SimulationGui;
import unitn.aose.warehousesim.simulator.Warehouse;
import unitn.aose.warehousesim.tellerMachine.TellerMachineGui;

public class Launcher {
	
	/**
	 * Using the RobotAgentFactory create an instance of IRobotAgent
	 * using the environment variable "wdas.factory.agent.class"
	 * for each robot currently available in the given environment.
	 * @param warehouse the current environment to get the robots from
	 * @return a list with all the created agents 
	 */
	private static Collection<IRobotAgent> getAgents(IWarehouse warehouse){
		final Collection<IRobotAgent> raList = new LinkedList<IRobotAgent>();
		RobotAgentFactory caFactory = new RobotAgentFactory("unitn.aose.warehousesim.agent.Agent_1"); //create a new RobotAgentFactory
		for(CartRef c : warehouse.getCarts()){ 
			IRobot r = warehouse.getRobot(c);
			IRobotAgent ra;
			ra = caFactory.createAgent(r);
			if(null == ra){
				System.out.println("ERROR no agent created for robot "+r.getName());
			}else{
				raList.add(ra);
				r.getSimulationTime().registerListener(new IListener<Long>() {
					public void notifyChanged(Long value) {
						ra.updateTime(value);
					}
				});
			}
		}
		return raList;
	}

	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * Adapter
		 */
		remoteApi vrep = new remoteApi();
		int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
		AdapterVRep adapter = new AdapterVRep(vrep, clientID);
		
		
		
		/*
		 * Warehouse
		 */
		Warehouse warehouse = new Warehouse(adapter);
		
		
		
		/*
		 * Configuration
		 */
		IConfigurator configuratorVRep = new ConfiguratorVRep(adapter, warehouse);
		ConfigurationOne confOne = new ConfigurationOne(configuratorVRep);
		adapter.play();
		confOne.initialize(warehouse);
		
		
		
		/*
		 * Sezione per il caricamento degli agenti
		 */
		Collection<IRobotAgent> agentsList = getAgents(warehouse);
		
		
		
		/*
		 * Agents
		 */
		List<IRobot> robotList = new ArrayList<IRobot>();
		for(CartRef c : warehouse.getCarts())
			robotList.add(warehouse.getRobot(c));
//		new Thread(new AgentJava(robotList)).start();
		new AgentGui(robotList);
		
		
		
		/*
		 * TellerMachines
		 */
		List<ITellerMachine> machineList = new ArrayList<ITellerMachine>();
		for(AreaRef a : warehouse.getAreas())
			machineList.add(warehouse.getTellerMachine(a));
		new TellerMachineGui(machineList);
        
		
		/*
		 * SimulationGui
		 */
		SimulationGui simulationGui = new SimulationGui(adapter, warehouse);
        
		
        /*
         * Update and triggering cycles
         */
		
		new Thread(new AdapterUpdateCycle(adapter, warehouse)).start();
		
		new Thread(new AdapterSyncronousTriggeringCycle(adapter, warehouse)).start();
		
	}

}
