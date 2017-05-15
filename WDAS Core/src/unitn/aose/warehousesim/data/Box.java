package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.data.BoxRef;


public class Box implements BoxRef {

	/**
	 * Ticket codes are assigned to mark the box ongoing process.
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
	
	private final String name;
	private final String stringFormat;
	private int ticket;
	
	private Area area;

	private Cart robot;
	
	public Box(String name) {
		this.name = name;
		stringFormat = this.getClass().getSimpleName()+"["+name+"]";
	}
	
	public String getName() {
		return name;
	}
	
	public Area getArea() {
		return area;
	}
	
	/**
	 * Retrieve the currently assigned ticket
	 * @return
	 */
	public int getTicket(){
		return ticket;
	}
	
	/**
	 * Assign one of the available tickets to this box
	 * so that all the relevant actors will know what's supposed to happen to this box.
	 * @param ticketStatus
	 */
	public void assignTicket(int ticket){
		this.ticket = ticket;
	}

	public void setArea(Area area) {
		this.area = area;
		if(null != area){
			this.robot = null;
		}
	}

	public Cart getCart() {
		return robot;
	}

	public void setCart(Cart robot) {
		this.robot = robot;
		if(null != robot){
			this.area = null;
		}
	}

	@Override
	public String toString(){
		return stringFormat;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof BoxRef){
			return ((BoxRef)obj).getName().equals(name);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}
}
