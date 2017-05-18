package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.Ticket;

public interface ITicketManager {
	
	ITicket getNewTicket(final BoxRef box, final boolean depositOrWithdraw);
	
	Ticket getTicketByCode(String code);
	
	/**
	 * This blocking call will wait for the ticket to change value
	 * @param ticket
	 * @throws InterruptedException
	 */
	void waitForTicket(Ticket ticket) throws InterruptedException;

}
