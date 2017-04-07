package unitn.aose.warehousesim.agent;

import unitn.aose.warehousesim.data.Cart;

/**
 * ICart 
 * fornisce metodi di accesso all'environment v-rep
 * @author Martina
 * 
 */
public interface ICartAgent {
	/**
	 * Il Cart che viene restituito è quello specificato nel costruttore dell'agente
	 * @return il Cart dell'environment associato all'agente
	 */
	Cart getCart(); 
}
