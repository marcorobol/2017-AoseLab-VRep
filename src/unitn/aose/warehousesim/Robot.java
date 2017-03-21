package unitn.aose.warehousesim;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.Rail;
import unitn.aose.warehousesim.api.data.RobotRef;


public abstract class Robot extends RobotRef implements IRobot {
	
	private Observable<MovementState> movementFSM;
	private Observable<LoadUnloadState> loadUnloadFSM;
	private Observable<Integer> position = new Observable<Integer>();
	private Float velocity = 0f;
	
	public Robot(String name) {
		super(name);
		this.movementFSM = new Observable<MovementState>(MovementState.stop);
		this.loadUnloadFSM = new Observable<LoadUnloadState>();
	}

	@Override
	public Observable<Integer> getPosition() {
		return position;
	}

	@Override
	public Float getVelocity() {
		return velocity;
	}

	public void setVelocity(float f) {
		this.velocity = f;
	}

	@Override
	public Observable<MovementState> getMovement() {
		return this.movementFSM;
	}

	@Override
	public Observable<LoadUnloadState> getLoadUnload() {
		return loadUnloadFSM;
	}
	
}
