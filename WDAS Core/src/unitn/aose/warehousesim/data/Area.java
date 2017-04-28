package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.observable.ObservableAreaState;
import unitn.aose.warehousesim.simulator.IAdapter;


public class Area implements AreaRef {

	private String name;
	private IAdapter adapter;
	private ObservableAreaState areaState;
	private Box box;
	
	public Area(String name, IAdapter adapter) {
		this.name = name;
		this.adapter = adapter;
		this.areaState = new ObservableAreaState();
	}
	
	public String getName() {
		return name;
	}

	public ObservableAreaState getState() {
		return areaState;
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
	
	
	
	public BoxRef createBox() {
		if(getState().get().equals(AreaState.free)) {
			
			Box box = adapter.createBoxIn(this);
			
			setBox(box);
			box.setArea(this);
			getState().set(AreaState.boxAvailable);
		}
		return null;
	}
	
	
	
	public void removeBox() {
		if(getState().get().equals(AreaState.boxAvailable)) {
			
			adapter.deleteBox(getBox());

			box.setArea(null);
			setBox(null);
			getState().set(AreaState.free);
		}
	}
	
}
