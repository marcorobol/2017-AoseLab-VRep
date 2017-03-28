package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITellerMachine {
	
	IObservable<AreaState> getState();
	
	AreaState requestDeposit();
	
	AreaState requestWithdraw(BoxRef box);
	
	BoxRef drop();
	
	void collect();
	
}
