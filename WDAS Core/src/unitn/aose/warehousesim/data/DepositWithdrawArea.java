package unitn.aose.warehousesim.data;

import java.util.Observable;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.ITicket;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.simulator.IAdapter;

public class DepositWithdrawArea extends Area implements DepositWithdrawAreaRef, ITellerMachine {

	private ITicket ticket = null;
	private final IWarehouse warehouse;

	public DepositWithdrawArea(String name, IAdapter adapter, IWarehouse warehouse) {
		super(name, adapter);
		this.warehouse = warehouse;
	}
	
	@Override
	public ITicket getGeneratedTicket() {
		return ticket;
	}
	
	@Override
	public ITicket requestDeposit() {
		AreaState currentAreaState = getState().get();
		if(currentAreaState.equals(AreaState.boxAvailable)) {
			if(!currentAreaState.equals(AreaState.elaboratingDeposit)){
				/**
				 * Do these 3 operations in this sequence in fundamental
				 * 1. create ticket
				 * 2. update state
				 * 3. notify areaMonitor observers
				 */
				ticket = warehouse.getTicketManager().getNewTicket(getBox(), true);
				getState().set(AreaState.elaboratingDeposit);
				areaMonitor.setChanged();
			}
		}
		return ticket;
	}

	@Override
	public ITicket requestWithdraw(BoxRef box) {
		AreaState currentAreaState = getState().get();
		if(currentAreaState.equals(AreaState.free)) {
			if(!currentAreaState.equals(AreaState.elaboratingWithdraw)){
				/**
				 * Do these 3 operations in this sequence in fundamental
				 * 1. create ticket
				 * 2. update state
				 * 3. notify areaMonitor observers
				 */
				ticket = warehouse.getTicketManager().getNewTicket(box, false);
				getState().set(AreaState.elaboratingWithdraw);
				areaMonitor.setChanged();
			}
		}
		return ticket;
	}
}
