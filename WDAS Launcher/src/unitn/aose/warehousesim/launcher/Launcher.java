package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import unitn.aose.warehousesim.IEnvironment;
import unitn.aose.warehousesim.Warehouse;
import unitn.aose.warehousesim.adapter.vrep.ConfigurationOne;
import unitn.aose.warehousesim.adapter.vrep.EnvironmentVRep;
import unitn.aose.warehousesim.agent.AgentGui;
import unitn.aose.warehousesim.agent.IRobotAgent;
import unitn.aose.warehousesim.agent.RobotAgentFactory;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.tellerMachine.TellerMachineGui;

public class Launcher {
	
	/**
	 * Using the RobotAgentFactory create an instance of IRobotAgent
	 * using the environment variable "wdas.factory.agent.class"
	 * for each robot currently available in the given environment.
	 * @param env the current environment to get the robots from
	 * @return a list with all the created agents 
	 */
	private static Collection<IRobotAgent> getAgents(IEnvironment env){
		final Collection<IRobotAgent> raList = new LinkedList<IRobotAgent>();
		IRobotAgent ra;
		RobotAgentFactory caFactory = new RobotAgentFactory(); //create a new CartAgentFactory
		for(CartRef c : env.getCarts()){ //per ogni Cart
			IRobot r = env.getRobot(c);
			ra = caFactory.createAgent(r);
			if(null == ra){
				System.out.println("ERROR no agent created for robot "+r.getName());
			}else{
				raList.add(ra);
			}
		}
		return raList;
	}

	public static void main(String[] args) throws InterruptedException {
		
		ConfigurationOne confOne = new ConfigurationOne();
		
		confOne.initialize();
		
		IEnvironment env = confOne.getEnv();
		
		/*
		 * Sezione per il caricamento degli agenti
		 */
		Collection<IRobotAgent> agentsList = getAgents(env);
		
		
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
