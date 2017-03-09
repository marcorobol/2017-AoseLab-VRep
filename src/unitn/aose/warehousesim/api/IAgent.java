package unitn.aose.warehousesim.api;

public interface IAgent {
	
	void notifyMovementStateChanged(MovementState s);
	void notifyLoadUnloadStateChanged(LoadUnloadState s);
	
	void notifyApproachingToARailCrossing(Rail r);
	void notifyStopBeforeARailCrossing(Rail r);
	
	void notifyApproachingToALoadUnloadArea(LoadUnloadArea a);
	void notifyStopInALoadUnloadArea(LoadUnloadArea a);
	
}
