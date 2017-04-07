package unitn.aose.warehousesim.agent;

import unitn.aose.warehousesim.data.Cart;

/**
 * 
 * @author Martina
 * 
 */

public class CartAgentFactory {
	/**
	 * Costruttore della classe, al momento vuota
	 */
	public CartAgentFactory(){
		
	}
	/**
	 * 
	 * 
	 * @param cart
	 * @return ICartAgent oppure nullo se si è verificato un errore
	 */
	public ICartAgent createAgent(Cart cart){
		System.out.println("agente "+cart);
		return null;
	}
}
