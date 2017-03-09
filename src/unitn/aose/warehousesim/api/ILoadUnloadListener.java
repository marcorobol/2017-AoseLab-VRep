package unitn.aose.warehousesim.api;

public interface ILoadUnloadListener {
	
	void notifyMovementStateChanged(MovementState s);
	
}
