package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.IEnvironment;
import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ITeller;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.observable.ObservableAreaState;


public class Area extends AreaRef implements ITeller {

	private IEnvironment environment;
	
	private ObservableAreaState areaState;
	
	public Area(String name, IEnvironment environment) {
		super(name);
		this.environment = environment;
		this.areaState = new ObservableAreaState();
	}

	@Override
	public ObservableAreaState getState() {
		return areaState;
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
			// TODO Auto-generated method stub
		}
		return areaState.get();
	}

	@Override
	public BoxRef drop() {
		return environment.createBoxIn(this);
	}

	@Override
	public void collect() {
		environment.deleteBoxIn(this);
	}
	
}
