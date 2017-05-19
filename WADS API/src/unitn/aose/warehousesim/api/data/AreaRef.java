package unitn.aose.warehousesim.api.data;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.IObservable;

public interface AreaRef {

	String getName();

	BoxRef getBox();
	
	IObservable<AreaState> getState();
}
