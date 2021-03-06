package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;
import unitn.aose.warehousesim.api.data.AreaRef;

public interface IRobot extends IRobotMonitor{
	
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
	
	/**
	 * Return true if the area is a storage area, otherwise it returns false.
	 * @param area
	 * @return
	 */
	boolean isAStorageArea(AreaRef area);
	
	IObservable<ICross> getCrossHaed();
	IObservable<ICross> getCrossBehind();
	IObservable<ICross> getCrossHere();
	
	IObservable<CartRef> getCartAround(PositionWithRespectToMe pos);
	ICartPerception getCartPerception(CartRef cart);
	
	String getName();
	BoxRef getLoadedBox();
	IObservable<Integer> getPosition();
//	Float getVelocity();

	IObservable<Long> getSimulationTime();
	IObservable<SimulationState> getSimulationState();
	
}
