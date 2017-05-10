package unitn.aose.warehousesim.data;

import java.util.Observable;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.observable.AreaStateMonitor;
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
			if(!getState().get().equals(AreaState.elaboratingDeposit)){
				getState().set(AreaState.elaboratingDeposit);
				areaMonitor.setChanged();
			}
		}
		return getState().get();
	}

	@Override
	public AreaState requestWithdraw(BoxRef box) {
		if(getState().get().equals(AreaState.free)) {
			if(!getState().get().equals(AreaState.elaboratingWithdraw)){
				getState().set(AreaState.elaboratingWithdraw);
				requestedBox = box;
				areaMonitor.setChanged();
			}
		}
		return getState().get();
	}
	
	@Override
	public Observable getAreaMonitor(){
		return this.areaMonitor;
	}
}
