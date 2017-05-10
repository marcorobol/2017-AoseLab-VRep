package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITellerMachine extends ITellerMachineMonitor {

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
	
	BoxRef createBox();
	
	void removeBox();
	
}
