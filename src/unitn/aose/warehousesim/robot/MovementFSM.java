package unitn.aose.warehousesim.robot;

import unitn.aose.warehousesim.api.IMovementFSM;
import unitn.aose.warehousesim.api.IMovementListener;
import unitn.aose.warehousesim.api.MovementState;

public class MovementFSM implements IMovementFSM{
	
	private MovementState s = MovementState.stop;

	public void setState(MovementState s) {
		// TODO Notify listeners
		this.s = s;
	}
	
	@Override
	public MovementState getState() {
		return s;
	}

	@Override
	public void registerListener(IMovementListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterListener(IMovementListener listener) {
		// TODO Auto-generated method stub
		
	}

}
