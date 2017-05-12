package unitn.aose.warehousesim.data;

import java.util.Observable;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.observable.AreaStateMonitor;
import unitn.aose.warehousesim.simulator.IAdapter;

public class DepositWithdrawArea extends Area implements DepositWithdrawAreaRef, ITellerMachine {

	private BoxRef requestedBox;
	private final IWarehouse warehouse;
	
	public DepositWithdrawArea(String name, IAdapter adapter, IWarehouse warehouse) {
		super(name, adapter);
		this.warehouse = warehouse;
	}

	@Override
	public BoxRef getRequestedBox() {
		return requestedBox;
	}

	@Override
	public AreaState requestDeposit() {
		AreaState currentAreaState = getState().get();
		if(currentAreaState.equals(AreaState.boxAvailable)) {
			if(!currentAreaState.equals(AreaState.elaboratingDeposit)){
				getState().set(AreaState.elaboratingDeposit);
				areaMonitor.setChanged();
				getBox().assignTicket(Box.TICKET_STORE);
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
				warehouse.assignTicket(box.getName(), Box.TICKET_RETRIEVE);
			}
		}
		return getState().get();
	}
	
	@Override
	public Observable getAreaMonitor(){
		return this.areaMonitor;
	}
}
