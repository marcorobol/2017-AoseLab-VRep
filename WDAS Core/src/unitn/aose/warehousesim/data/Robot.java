package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.RobotRef;
import unitn.aose.warehousesim.observable.ObservableInteger;
import unitn.aose.warehousesim.observable.ObservableLoadUnload;
import unitn.aose.warehousesim.observable.ObservableMovementState;


public abstract class Robot extends RobotRef implements IRobot {
	
	private ObservableMovementState movementFSM;
	private ObservableLoadUnload loadUnloadFSM;
	private ObservableInteger position = new ObservableInteger();
	private Float velocity = 0f;
	
	public Robot(String name) {
		super(name);
		this.movementFSM = new ObservableMovementState(MovementState.stop);
		this.loadUnloadFSM = new ObservableLoadUnload();
	}

	@Override
	public ObservableInteger getPosition() {
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
	public ObservableMovementState getMovement() {
		return this.movementFSM;
	}

	@Override
	public ObservableLoadUnload getLoadUnload() {
		return loadUnloadFSM;
	}
	
}
