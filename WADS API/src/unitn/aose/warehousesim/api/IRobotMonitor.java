package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;

public interface IRobotMonitor {
	
	IObservable<MovementState> getMovement();
	
	IObservable<LoadUnloadState> getLoadUnload();
	
	IObservable<AreaRef> getAreaOnLeft();
	IObservable<AreaRef> getAreaOnRight();
	
	BoxRef getBoxOnLeft();
	BoxRef getBoxOnRight();
	
	IObservable<ICross> getCrossHaed();
	IObservable<ICross> getCrossBehind();
	IObservable<ICross> getCrossHere();
	
	IObservable<CartRef> getCartAround(PositionWithRespectToMe pos);
	ICartPerception getCartPerception(CartRef cart);
	
	String getName();
	BoxRef getLoadedBox();
	IObservable<Integer> getPosition();
	
}
