package unitn.aose.warehousesim.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.Ticket;

/**
 * Singleton class to keep trace of all the tickets assigned to the boxes.
 * @author matteo.pedrotti@deltainformatica.eu
 *
 */
public class TicketManager {
	
	private static TicketManager instance;
	
	public synchronized static TicketManager getInstance(){
		if(null == instance){
			instance = new TicketManager();
		}
		return instance;
	}
	
	/**
	 * Map of ticket codes and tickets
	 */
	private Map<String, Ticket> codeToTicketMap;
	private int progressiveId;
	
	private TicketManager(){
		codeToTicketMap = new HashMap<String, Ticket>();
	}
	
	/**
	 * Used internally to get a new ticket code
	 * @return
	 */
	private String nextCode(){
		return Integer.toHexString(progressiveId++);
	}
	
	public synchronized ITicket getNewTicket(final BoxRef box, final boolean depositOrWithdraw){
		final String code = nextCode();
		Ticket t = new Ticket(code, box, depositOrWithdraw);
		codeToTicketMap.put(code, t);
		return t;
 	}
	
	public synchronized Ticket getTicketByCode(String code){
		return codeToTicketMap.get(code);
	}
	
	
	
	/**
	 * This blocking call will wait for the ticket to change value
	 * @param ticket
	 * @throws InterruptedException
	 */
	public void waitForTicket(Ticket ticket) throws InterruptedException{
		synchronized(ticket){
			Observer o = new Observer() {
				public void update(Observable o, Object arg) {
					synchronized(ticket){
						ticket.notifyAll();
					}
				}
			};
			ticket.addObserver(o);
			try{
				ticket.wait();
			}catch(InterruptedException e){
				throw e;
			} finally {
				ticket.deleteObserver(o);
			}
		}
	}

}
