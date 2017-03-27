package unitn.aose.warehousesim.observable;

import unitn.aose.warehousesim.api.MovementState;

public class ObservableMovementState extends Observable<MovementState, MovementState> {

	public ObservableMovementState(MovementState init) {
		super(init);
	}

}
