package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;

public interface IAgent {
	
	void notifyMovementStateChanged(MovementState s);
	void notifyLoadUnloadStateChanged(LoadUnloadState s);
	
	void notifyApproachingToARailCrossing(Rail r);
	void notifyStopBeforeARailCrossing(Rail r);
	
	void notifyApproachingToArea(LandingArea a);
	void notifyStopInALoadUnloadArea(LandingArea a);
	
}
