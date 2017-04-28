package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.simulator.IAdapter;

public class DepositWithdrawArea extends Area implements DepositWithdrawAreaRef, ITellerMachine {

	private BoxRef requestedBox;
	
	public DepositWithdrawArea(String name, IAdapter adapter) {
		super(name, adapter);
	}

	@Override
	public BoxRef getRequestedBox() {
		return requestedBox;
	}

	@Override
	public AreaState requestDeposit() {
		if(getState().get().equals(AreaState.boxAvailable)) {
			getState().set(AreaState.elaboratingDeposit);
		}
		return getState().get();
	}

	@Override
	public AreaState requestWithdraw(BoxRef box) {
		if(getState().get().equals(AreaState.free)) {
			getState().set(AreaState.elaboratingWithdraw);
			requestedBox = box;
		}
		return getState().get();
	}
	
}
