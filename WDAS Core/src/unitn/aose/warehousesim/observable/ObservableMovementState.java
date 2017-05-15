package unitn.aose.warehousesim.observable;

import unitn.aose.warehousesim.api.MovementState;

public class ObservableMovementState extends Observable<MovementState, MovementState> {
	
	private MovementState actual;
	
	public ObservableMovementState(MovementState init) {
		super(init);
		actual = init;
	}
	
	@Override
	public void set(MovementState value) {
		// I always memorize in ''actual'' the actual value of movement ignoring the broken state
		if(value!=MovementState.broken)
			actual = value;
		// If broken, movement value remains broken forever
		if(get()!=MovementState.broken)
			super.set(value);
	}
	
	/**
	 * Once the movement has been setted to broken,
	 * this is the only way to restore the normal behavior of the cart
	 * and recover the actual value of movement
	 */
	public void fix() {
		super.set(actual);
	}

}
