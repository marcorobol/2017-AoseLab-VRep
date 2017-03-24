package unitn.aose.warehousesim;

import unitn.aose.warehousesim.api.MovementState;

public class ObservableMovementState extends Observable<MovementState, MovementState> {

	public ObservableMovementState(MovementState init) {
		super(init);
	}

}
