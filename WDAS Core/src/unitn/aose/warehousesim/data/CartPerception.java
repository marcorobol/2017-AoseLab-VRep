package unitn.aose.warehousesim.data;

import java.util.Date;
import unitn.aose.warehousesim.api.ICartPerception;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.observable.ObservableBoolean;
import unitn.aose.warehousesim.observable.ObservableMovementWithRespectToMe;
import unitn.aose.warehousesim.observable.ObservablePositionWithRespectToMe;

public class CartPerception implements ICartPerception {

	private CartRef cart;
	private RailRef rail;
	
	private Date lastUpdate;
	
	private ObservableBoolean inFieldOfView; 
	private ObservableMovementWithRespectToMe movementWithRespectToMe;
	private ObservablePositionWithRespectToMe positionWithRespectToMe;
	
	
	
	public CartPerception(CartRef cart, RailRef rail) {
		this.cart = cart;
		this.rail = rail;
		
		inFieldOfView = new ObservableBoolean(); 
		movementWithRespectToMe = new ObservableMovementWithRespectToMe();
		positionWithRespectToMe = new ObservablePositionWithRespectToMe();
		
		/*
		 * Observe to update time
		 */
	}
	
	
	
	@Override
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	@Override
	public CartRef getCart() {
		return cart;
	}
	
	@Override
	public RailRef getRail() {
		return rail;
	}
	
	@Override
	public ObservableBoolean getInFieldOfView() {
		return inFieldOfView;
	}
	
	@Override
	public ObservableMovementWithRespectToMe getMovementWithRespectToMe() {
		return movementWithRespectToMe;
	}
	
	@Override
	public ObservablePositionWithRespectToMe getPositionWithRespectToMe() {
		return positionWithRespectToMe;
	}
	
	public int hashCode(){
		return cart.getName().hashCode();
	}
	
	public boolean equals(Object o){
		if(!(o instanceof CartPerception)) return false;
		CartPerception cp = (CartPerception)o;
		return cart.getName().equals(cp.cart.getName()) &&
				movementWithRespectToMe.get() == cp.movementWithRespectToMe.get() &&
				positionWithRespectToMe.get().equals(cp.positionWithRespectToMe.get());
	}
}
