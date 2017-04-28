package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITellerMachineMonitor {
	
	String getName();
	
	IObservable<AreaState> getState();
	
	
	
	BoxRef getRequestedBox();
	
}
