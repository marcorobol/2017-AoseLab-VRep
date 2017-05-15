package unitn.aose.warehousesim.api.data;

import java.util.Observable;

public class Ticket extends Observable {

	/**
	 * Ticket states are assigned to mark the ongoing process.
*/
	public static final int 
			/** No ticket for this box (yet) **/
			TICKET_NONE = 0,
			/** The box has to be stored: carried from a deposit/withdraw area in an internal storage area **/
			TICKET_STORE = 1,
/** The box has to be retrieved: carried from a storage area to an appropriate delivery area **/
			TICKET_RETRIEVE = 2,
			/** The box ticket has been completed **/
			TICKET_DONE = 3;
	
	private String code;
	private int state;
	
	public Ticket(String code){
		this.code = code;
		state = Ticket.TICKET_NONE;
	}

	public String getCode() {
		return code;
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
}