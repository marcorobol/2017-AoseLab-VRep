package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.Box;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.RobotRef;

public interface IRobot {
	
	/*
	 * Controls
	 */
	
	void moveForward();
	void moveBackward();
	void moveApproaching();
	void moveNotApproaching();
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
	
	RobotRef getRobotHaed();
	RobotRef getRobotBehind();
	
	String getName();
	Box getLoadedBox();
	IObservable<Integer> getPosition();
	Float getVelocity();
	
}
