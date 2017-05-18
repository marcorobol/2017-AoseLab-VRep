package unitn.aose.warehousesim.api.data;

import java.util.Observable;

import unitn.aose.warehousesim.api.ITicket;

public class Ticket extends Observable implements ITicket {

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
	
	private final String code;
	private final BoxRef box;
	/**
	 * TRUE: box has to be stored: carried from a deposit/withdraw area in an internal storage area
	 * FALSE: box has to be retrieved: carried from a storage area to an appropriate delivery area
	 */
	private final boolean depositOrWithdraw;
	private int state;
	
	public Ticket(final String code, final BoxRef box, final boolean depositOrWithdraw){
		this.code = code;
		this.box = box;
		this.depositOrWithdraw = depositOrWithdraw;
		state = Ticket.TICKET_GENERATED;
	}

	public String getCode() {
		return code;
	}
	
	public BoxRef getBox(){
		return box;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		if(state != this.state){
			this.state = state;
			setChanged();
			notifyObservers();
		}
	}
	
	@Override
	public int hashCode(){
		return code.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Ticket){
			return ((Ticket)obj).code.equals(this.code);
		}
		return false;
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName()+"[code:"+code+";state:"+state+"]";
	}

	@Override
	public boolean depositOrWithdrawRequest() {
		return depositOrWithdraw;
	}

}