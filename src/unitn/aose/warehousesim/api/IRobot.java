package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.Observable;
import unitn.aose.warehousesim.api.data.Box;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;
import unitn.aose.warehousesim.api.data.Robot;

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
	
	IObservable<LandingArea> getAreaOnLeft();
	IObservable<LandingArea> getAreaOnRight();
	
	Box getBoxOnLeft();
	Box getBoxOnRight();
	
	Robot getRobotHaed();
	Robot getRobotBehind();
	
	String getName();
	Box getLoadedBox();
	Rail getRail();
	Observable<Integer> getPosition();
	Float getVelocity();
	
}
