package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.List;

import unitn.aose.warehousesim.IEnvironment;
import unitn.aose.warehousesim.Warehouse;
import unitn.aose.warehousesim.adapter.vrep.ConfigurationOne;
import unitn.aose.warehousesim.adapter.vrep.EnvironmentVRep;
import unitn.aose.warehousesim.agent.AgentGui;
import unitn.aose.warehousesim.agent.CartAgentFactory;
import unitn.aose.warehousesim.agent.ICartAgent;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.data.Cart;
import unitn.aose.warehousesim.tellerMachine.TellerMachineGui;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {
		
		ConfigurationOne confOne = new ConfigurationOne();
		
		confOne.initialize();
		
		EnvironmentVRep env = confOne.getEnv();
		
		
		/*
		 * Sezione per il caricamento degli agenti
		 */
		ICartAgent ca;
		CartAgentFactory caFactory = new CartAgentFactory(); //create a new CartAgentFactory
		for(CartRef c : env.getCarts()){ //per ogni Cart
			ca = caFactory.createAgent(env.getRobot(c));
			if(ca == null){
				System.out.println("ERROR no robot");
			}
		}
		
		
		List<IRobot> robotList = new ArrayList<IRobot>();
		for(CartRef c : env.getCarts())
			robotList.add(env.getRobot(c));
		
		/*
		 * Agents
		 */
//		robotList.add(robot_a1);
//		robotList.add(robot_d1);
//		robotList.add(robot_d2);
//		new Thread(new AgentJava(robotList)).start();

//		List<IRobot> guiRobotList = new ArrayList<IRobot>();
//		guiRobotList.add(robot_a2);
		new AgentGui(robotList);
		
		/*
		 * Warehouse GUI
		 */
		List<ITellerMachine> machineList = new ArrayList<ITellerMachine>();
		for(AreaRef a : env.getAreas())
			machineList.add(env.getTellerMachine(a));
		new TellerMachineGui(machineList);
        
        
        /*
         * Simulation cycle
         */
    	
		Warehouse.run(env);

	}

}
