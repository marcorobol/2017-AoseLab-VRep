package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.Ticket;

public interface ITicketManager {
	
	ITicket getNewTicket(final BoxRef box, final boolean depositOrWithdraw);
	
	Ticket getTicketByCode(String code);
	
	/**
	 * This blocking call will wait for the ticket to change value
	 * @param ticket the ticket to monitor
	 * @param maxMillis how long to wait for the ticket
	 * @throws InterruptedException if this thread has been marked as interrupted
	 */
	void waitForTicket(Ticket ticket, long maxMillis) throws InterruptedException;

}
