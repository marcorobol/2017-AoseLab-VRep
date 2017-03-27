package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.CartRef;

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
	
//	CartRef getRobotHaed();
//	CartRef getRobotBehind();
	
	String getName();
	BoxRef getLoadedBox();
	IObservable<Integer> getPosition();
	Float getVelocity();
	
}
