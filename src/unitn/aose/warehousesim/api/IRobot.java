package unitn.aose.warehousesim.api;

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
	
	LoadUnloadArea getStorageAreaOnLeft();
	LoadUnloadArea getStorageAreaOnRight();
	
	Box getBoxOnLeft();
	Box getBoxOnRight();
	
	Box getLoadedBox();
	
	Robot getRobotHaed();
	Robot getRobotBehind();
	
	Rail getRail();
	
}
