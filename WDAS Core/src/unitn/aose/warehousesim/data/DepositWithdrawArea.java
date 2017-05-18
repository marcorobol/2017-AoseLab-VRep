package unitn.aose.warehousesim.data;

import java.util.Observable;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.ITicket;
import unitn.aose.warehousesim.api.TicketManager;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.simulator.IAdapter;

public class DepositWithdrawArea extends Area implements DepositWithdrawAreaRef, ITellerMachine {

	private BoxRef requestedBox;
	private ITicket ticket = null;

	
	public DepositWithdrawArea(String name, IAdapter adapter) {
		super(name, adapter);
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
				getState().set(AreaState.elaboratingDeposit);
				TicketManager tm = TicketManager.getInstance();
				ticket = tm.getNewTicket(getBox(), true);
			}
		}
		return ticket;
	}

	@Override
	public ITicket requestWithdraw(BoxRef box) {
		AreaState currentAreaState = getState().get();
		if(currentAreaState.equals(AreaState.free)) {
			if(!currentAreaState.equals(AreaState.elaboratingWithdraw)){
				getState().set(AreaState.elaboratingWithdraw);
				requestedBox = box;
				areaMonitor.setChanged();
				TicketManager tm = TicketManager.getInstance();
				ticket = tm.getNewTicket(requestedBox, false);
				areaMonitor.setChanged();
			}
		}
		return ticket;
	}
	
	@Override
	public Observable getAreaMonitor(){
		return this.areaMonitor;
	}
}
