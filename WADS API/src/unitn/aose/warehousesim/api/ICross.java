package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.RailRef;

public interface ICross {
	
	boolean isRightTrueOrLeftFalse();
	
	RailRef getRail();
	
	int getRailIndex();

}
