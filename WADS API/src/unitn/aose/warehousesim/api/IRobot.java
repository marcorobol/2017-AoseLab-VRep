package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;
import unitn.aose.warehousesim.api.data.AreaRef;

public interface IRobot {
	
	/*
	 * Controls
	 */
	
	void moveForward();
	void moveBackward();
	void stopHere();
	IObservable<MovementState> getMovement();
	
	void loadLeft();
	void loadRight();
	void unloadLeft();
	void unloadRight();
	IObservable<LoadUnloadState> getLoadUnload();
	
	/*
	 * Surrounding environment
	 */
	
	IObservable<AreaRef> getAreaOnLeft();
	IObservable<AreaRef> getAreaOnRight();
	
	BoxRef getBoxOnLeft();
	BoxRef getBoxOnRight();
	
	IObservable<AreaState> getAreaState(AreaRef area);
	
	IObservable<ICross> getCrossHaed();
	IObservable<ICross> getCrossBehind();
	IObservable<ICross> getCrossHere();
	
	IObservable<CartRef> getCartAround(PositionWithRespectToMe pos);
	ICartPerception getCartPerception(CartRef cart);
	
	String getName();
	BoxRef getLoadedBox();
	IObservable<Integer> getPosition();
	Float getVelocity();
	
}
