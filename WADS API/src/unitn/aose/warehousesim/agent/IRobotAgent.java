package unitn.aose.warehousesim.agent;

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
}
