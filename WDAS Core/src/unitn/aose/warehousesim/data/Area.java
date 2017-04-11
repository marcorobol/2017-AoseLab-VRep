package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.observable.ObservableAreaState;
import unitn.aose.warehousesim.simulator.IAdapter;


public class Area extends AreaRef implements ITellerMachine {

	private IAdapter adapter;
	private ObservableAreaState areaState;
	private BoxRef requestedBox;
	private Box box;
	
	public Area(String name, IAdapter adapter) {
		super(name);
		this.adapter = adapter;
		this.areaState = new ObservableAreaState();
	}

	@Override
	public ObservableAreaState getState() {
		return areaState;
	}
	
	public BoxRef getRequestedBox() {
		return requestedBox;
	}
	
	public Box getBox() {
		return box;
	}
	
	public void setBox(Box box) {
		if(box!=null)
			getState().set(AreaState.boxAvailable);
		else
			getState().set(AreaState.free);
		this.box = box;
	}

	@Override
	public AreaState requestDeposit() {
		if(getState().equals(AreaState.boxAvailable)) {
			getState().set(AreaState.elaboratingDeposit);
		}
		return areaState.get();
	}

	@Override
	public AreaState requestWithdraw(BoxRef box) {
		if(getState().equals(AreaState.free)) {
			getState().set(AreaState.elaboratingWithdraw);
			requestedBox = box;
		}
		return areaState.get();
	}
	
	
	
	@Override
	public BoxRef createBox() {
		if(getState().get().equals(AreaState.free)) {
			
			Box box = adapter.createBoxIn(this);
			
			setBox(box);
			box.setArea(this);
			getState().set(AreaState.boxAvailable);
		}
		return null;
	}
	
	
	
	@Override
	public void removeBox() {
		if(getState().get().equals(AreaState.boxAvailable)) {
			
			adapter.deleteBox(getBox());
			
			setBox(null);
			box.setArea(null);
			getState().set(AreaState.free);
		}
	}
	
}
