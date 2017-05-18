package unitn.aose.warehousesim.api;

import java.util.Observable;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITellerMachineMonitor {
	
	String getName();
	
	IObservable<AreaState> getState();
	
	ITicket getGeneratedTicket();
	
	/**
	 * Box currently stored in this teller machine
	 * @return
	 */
	BoxRef getBox();
	
}
