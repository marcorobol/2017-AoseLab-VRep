package unitn.aose.warehousesim.api;

public interface IMovementListener {
	
	void notifyMovementStateChanged(MovementState s);
	
}
