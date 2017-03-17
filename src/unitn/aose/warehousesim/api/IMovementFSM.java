package unitn.aose.warehousesim.api;


public interface IMovementFSM {
	
	MovementState getState();
	
	void registerListener(IMovementListener listener);
	void unregisterListener(IMovementListener listener);
}
