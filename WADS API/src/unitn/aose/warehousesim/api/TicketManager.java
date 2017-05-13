package unitn.aose.warehousesim.api;

import java.util.HashMap;
import java.util.Map;

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
	private Map<String, Ticket> tickets;
	private int progressiveId;
	
	private TicketManager(){
		tickets = new HashMap<String, Ticket>();
	}
	
	/**
	 * Used internally to get a new ticket code
	 * @return
	 */
	private String nextCode(){
		return Integer.toHexString(progressiveId++);
	}
	
	public synchronized String getNewTrackingCode(){
		String code = nextCode();
		Ticket t = new Ticket(code);
		tickets.put(code, t);
		return code;

 	}
	
	public synchronized void setTrackingState(String code, int state){
		Ticket t = tickets.get(code);
		if(null != t){
			t.setState(state);
		}
	}
	
	public synchronized int getTrackingNumberState(String code){
		int tstate = Ticket.TICKET_NONE;
		Ticket t = tickets.get(code);
		if(null != t){
			tstate = t.getState();
		}
		return tstate;
	}
	
	public synchronized Ticket getTicket(String code){
		return tickets.get(code);
	}
}
