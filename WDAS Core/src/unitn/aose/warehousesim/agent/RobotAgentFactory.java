package unitn.aose.warehousesim.agent;

import unitn.aose.warehousesim.api.IRobot;

/**
 * 
 * @author Martina
 * 
 */

public class RobotAgentFactory {
	/**
	 * Costruttore della classe, al momento vuota
	 */
	public RobotAgentFactory(){
		
	}
	/**
	 * 
	 * 
	 * @param cart
	 * @return ICartAgent oppure nullo se si è verificato un errore
	 */
	public IRobotAgent createAgent(IRobot robot){
		System.out.println("agente "+robot);
		return null;
	}
}
