package unitn.aose.warehousesim.agent;

import unitn.aose.warehousesim.api.IRobotMonitor;

/**
 * Provides interface methods to control an IRobot
 * @author Martina
 * 
 */
public interface IRobotAgent extends ISimulationAgent {
	
	/**
	 * Retrieve the robot associated with this agent.
	 * Ideally the robot instance is passed to the robot at creation.
	 * @return the associated robot or null if no robot has been associated with this agent
	 */
	IRobotMonitor getRobot();
	
	/**
	 * The agent full name.
	 * @return
	 */
	String name();
}
