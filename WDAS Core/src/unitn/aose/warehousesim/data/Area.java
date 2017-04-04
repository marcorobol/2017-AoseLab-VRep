package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.IEnvironment;
import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.observable.ObservableAreaState;


public class Area extends AreaRef implements ITellerMachine {

	private IEnvironment environment;
	private ObservableAreaState areaState;
	private BoxRef requestedBox;
	private Box box;
	
	public Area(String name, IEnvironment environment) {
		super(name);
		this.environment = environment;
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
	public BoxRef drop() {
		if(getState().get().equals(AreaState.free)) {
			getState().set(AreaState.boxAvailable);
			return environment.createBoxIn(this);
		}
		return null;
	}

	@Override
	public void collect() {
		if(getState().get().equals(AreaState.boxAvailable)) {
			environment.deleteBoxIn(this);
		}
	}
	
}
