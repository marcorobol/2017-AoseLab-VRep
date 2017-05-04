package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.List;

import coppelia.remoteApi;
import unitn.aose.warehousesim.adapter.vrep.AdapterVRep;
import unitn.aose.warehousesim.adapter.vrep.ConfiguratorVRep;
import unitn.aose.warehousesim.agent.AgentGui;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.configuration.ConfigurationThree;
import unitn.aose.warehousesim.configuration.IConfigurator;
import unitn.aose.warehousesim.simulator.AdapterSyncronousTriggeringCycle;
import unitn.aose.warehousesim.simulator.AdapterUpdateCycle;
import unitn.aose.warehousesim.simulator.SimulationGui;
import unitn.aose.warehousesim.simulator.Warehouse;
import unitn.aose.warehousesim.tellerMachine.TellerMachineGui;

public class LauncherSceneThreeWithoutAgents {

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
		ConfigurationThree confThree = new ConfigurationThree(configuratorVRep);
		adapter.play();
		confThree.initialize(warehouse);
		
		
		
		/*
		 * Agents
		 */
		List<IRobot> robotList = new ArrayList<IRobot>(); 
		for(CartRef c : warehouse.getCarts())
			robotList.add(warehouse.getRobot(c));
		new AgentGui(robotList);
		
		
		/*
		 * TellerMachines
		 */
		List<ITellerMachine> machineList = new ArrayList<ITellerMachine>();
		for(DepositWithdrawAreaRef a : warehouse.getDepositWithdrawAreas())
			machineList.add(warehouse.getTellerMachine(a));
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
