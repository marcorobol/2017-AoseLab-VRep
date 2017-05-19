package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITicket {

	/**
	 * Ticket states are assigned to mark the ongoing process.
	 */
	public static final int 
			/** The ticket has just been generated **/
			TICKET_GENERATED = 0,
			/** The request is in elaboration **/
			TICKET_ELABORATING = 1,
			/** The request cannot be processed **/
			TICKET_ERROR = 2,
			/** The request has been completed **/
			TICKET_DONE = 3;
	
	String getCode();
	
	boolean depositOrWithdrawRequest();
	
	BoxRef getBox();
	
	int getState();
	
	void setState(int state);
}
