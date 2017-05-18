package unitn.aose.warehousesim.api;

import java.util.Observable;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITellerMachineMonitor {
	
	String getName();
	
	/**
	 * Had to introduce a standard JVM driven object
	 * to monitor the state from jack.
	 * This monitor is notified when the area state changes
	 * @return
	 */
	Observable getAreaMonitor();
	
	IObservable<AreaState> getState();
	
	ITicket getGeneratedTicket();
	
	/**
	 * Box currently stored in this teller machine
	 * @return
	 */
	BoxRef getBox();
	
}
