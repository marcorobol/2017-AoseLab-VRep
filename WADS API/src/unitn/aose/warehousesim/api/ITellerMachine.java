package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITellerMachine {
	
	String getName();
	
	IObservable<AreaState> getState();
	
	/**
	 * Take the box and bring it inside the warehouse
	 * @return
	 */
	AreaState requestDeposit();
	
	/**
	 * Bring the given box in this area
	 * @param box
	 * @return
	 */
	AreaState requestWithdraw(BoxRef box);
	
	BoxRef getRequestedBox();
	
	BoxRef createBox();
	
	void removeBox();
	
}
