package unitn.aose.warehousesim.api;

public enum MovementState {
	
	stop, runningForward, runningBackward, stopping, broken;
	//il valore delle costanti è dublicato nel robotData di jack nel progetto jack e va mantenuto allineato
	public MovementState getInverse() {
		if(this==runningForward)
			 return runningBackward;
		if(this==runningBackward)
			return runningForward;
		return this;
	}
	
}
