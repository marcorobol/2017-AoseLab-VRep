package unitn.aose.warehousesim.api.data;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.IObservable;

public interface AreaRef {

	String getName();

	BoxRef getBox();
	
//	/**
//	 * Had to introduce a standard JVM driven object
//	 * to monitor the state from jack.
//	 * This monitor is notified when the area state changes
//	 * @return
//	 */
//	Observable getAreaMonitor();
	
	IObservable<AreaState> getState();
}
