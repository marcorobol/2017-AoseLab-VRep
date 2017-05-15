package unitn.aose.warehousesim.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import unitn.aose.warehousesim.api.data.Ticket;

/**
 * Singleton class to keep trace of all the tickets assigned to the boxes.
 * @author matteo.pedrotti@deltainformatica.eu
 *
 */
public class TicketManager implements Observer {

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
	private Map<String, Ticket> boxToTicketMap;
	private int progressiveId;
	
	private TicketManager(){
		codeToTicketMap = new HashMap<String, Ticket>();
		boxToTicketMap = new HashMap<String, Ticket>();
	}
	
	/**
	 * Used internally to get a new ticket code
	 * @return
	 */
	private String nextCode(){
		return Integer.toHexString(progressiveId++);
	}
	
	public synchronized String getNewTrackingCode(final String boxName){
		final String code = nextCode();
		Ticket t = new Ticket(code, boxName);
		codeToTicketMap.put(code, t);
		boxToTicketMap.put(boxName, t);
		return code;
 	}
	
	public synchronized void setTrackingState(String code, int state){
		Ticket t = codeToTicketMap.get(code);
		if(null != t){
			t.setState(state);
		}
	}
	
	public synchronized int getTrackingNumberState(String code){
		int tstate = Ticket.TICKET_NONE;
		Ticket t = codeToTicketMap.get(code);
		if(null != t){
			tstate = t.getState();
		}
		return tstate;
	}
	
	public synchronized Ticket getTicketByCode(String code){
		return codeToTicketMap.get(code);
	}
	
	public synchronized Ticket getTicketByBox(String boxName){
		return boxToTicketMap.get(boxName);
	}
	
	/**
	 * This blocking call will wait for the ticket to change value
	 * @param ticket
	 * @throws InterruptedException
	 */
	public void waitForTicket(Ticket ticket) throws InterruptedException{
		synchronized(ticket){
			ticket.addObserver(this);
			try{
				ticket.wait();
			}catch(InterruptedException e){
				throw e;
			} finally {
				ticket.deleteObserver(this);
			}
		}
	}

	@Override
	public void update(Observable ticket, Object arg) {
		synchronized(ticket){
			ticket.notifyAll();
		}
	}
}
