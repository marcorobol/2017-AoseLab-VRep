package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITellerMachine {
	
	String getName();
	
	IObservable<AreaState> getState();
	
	BoxRef getRequestedBox();
	
	AreaState requestDeposit();
	
	AreaState requestWithdraw(BoxRef box);
	
	BoxRef drop();
	
	void collect();
	
}
