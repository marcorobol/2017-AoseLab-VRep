package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.List;

import unitn.aose.warehousesim.IEnvironment;
import unitn.aose.warehousesim.Warehouse;
import unitn.aose.warehousesim.adapter.vrep.ConfigurationOne;
import unitn.aose.warehousesim.agent.AgentGui;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.data.CartRef;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {
		
		ConfigurationOne confOne = new ConfigurationOne();
		
		confOne.initialize();
		
		IEnvironment env = confOne.getEnv();
		
		List<IRobot> robotList = new ArrayList<IRobot>();
		for(CartRef c : env.getCarts()) {
			robotList.add(env.getRobot(c));
		}
		
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
         * Simulation cycle
         */
    	
		Warehouse.run(env);

	}

}
