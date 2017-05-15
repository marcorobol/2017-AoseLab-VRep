package unitn.aose.warehousesim.agent;

/**
 * Generic interface methods for agents working in a simulated environment.
 * @author matteo.pedrotti@deltainformatica.eu
 *
 */
public interface ISimulationAgent {
	
	/**
	 * Set the new time in the agent clock
	 * @param time simulation time in milliseconds
	 */
	void updateTime(long time);
}
