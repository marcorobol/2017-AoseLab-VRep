package unitn.aose.warehousesim.api;

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
	MovementState getState();
	void registerMovementListener(IMovementListener listener);
	void unregisterMovementListener(IMovementListener listener);
	
	void load(Box b);
	Box unload();
	ILoadUnloadFSM getLoadUnloadFSM();
	
	/*
	 * Surrounding environment
	 */
	
	LandingArea getStorageAreaOnLeft();
	LandingArea getStorageAreaOnRight();
	
	Box getBoxOnLeft();
	Box getBoxOnRight();
	
	Box getLoadedBox();
	
	Robot getRobotHaed();
	Robot getRobotBehind();
	
	Rail getRail();
	
}
