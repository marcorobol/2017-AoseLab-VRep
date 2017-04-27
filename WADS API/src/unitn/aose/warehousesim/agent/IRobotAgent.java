package unitn.aose.warehousesim.agent;

import unitn.aose.warehousesim.api.IRobot;

/**
 * Provides interface methods to control an IRobot
 * @author Martina
 * 
 */
public interface IRobotAgent {
	
	/**
	 * Set the new time in the agent clock
	 * @param simulation time in milliseconds
	 */
	void updateTime(long time);
	
	/**
	 * Retrieve the robot associated with this agent.
	 * Ideally the robot instance is passed to the robot at creation.
	 * @return the associated robot or null if no robot has been associated with this agent
	 */
	IRobot getRobot();
}
