package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.observable.AreaStateMonitor;
import unitn.aose.warehousesim.observable.ObservableAreaState;
import unitn.aose.warehousesim.simulator.IAdapter;

public class Area implements AreaRef {

	private final String name, stringFormat;
	protected final AreaStateMonitor areaMonitor;
	private IAdapter adapter;
	private ObservableAreaState areaState;
	private Box box;

	public Area(String name, IAdapter adapter) {
		this.name = name;
		this.areaMonitor = new AreaStateMonitor();
		stringFormat = this.getClass().getSimpleName() + "[" + name + "]";
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
		if (box != null) {
			getState().set(AreaState.boxAvailable);
			box.setArea(this);
		} else {
			getState().set(AreaState.free);
		}
		this.box = box;
		areaMonitor.setChanged();
	}

	public BoxRef createBox() {
		Box box = null;
		if (getState().get().equals(AreaState.free)) {
			box = adapter.createBoxIn(this);
			box.setArea(this);
			setBox(box);
		}
		return box;
	}

	public void removeBox() {
		if (getState().get().equals(AreaState.boxAvailable)) {
			adapter.deleteBox(getBox());
			box.setArea(null);
			setBox(null);
			getState().set(AreaState.free);
		}
	}

	@Override
	public String toString() {
		return stringFormat;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AreaRef) {
			return ((AreaRef) obj).getName().equals(name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
