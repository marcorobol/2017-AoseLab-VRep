package unitn.aose.warehousesim.api;

public enum MovementState {
	
	stop, runningForward, runningBackward, stopping;
	
	public MovementState getInverse() {
		if(this==runningForward)
			 return runningBackward;
		if(this==runningBackward)
			return runningForward;
		return this;
	}
	
}
