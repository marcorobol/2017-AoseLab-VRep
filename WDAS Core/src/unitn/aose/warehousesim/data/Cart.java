package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.observable.Observable;
import unitn.aose.warehousesim.observable.ObservableArea;
import unitn.aose.warehousesim.observable.ObservableInteger;
import unitn.aose.warehousesim.observable.ObservableLoadUnload;
import unitn.aose.warehousesim.observable.ObservableMovementState;


public class Cart extends CartRef {

	private Rail rail;
	private ObservableMovementState movementFSM;
	private ObservableLoadUnload loadUnloadFSM;
	private ObservableInteger position = new ObservableInteger();
	private Float velocity = 0f;
	private Observable<Area, AreaRef> areaOnLeft;
	private Observable<Area, AreaRef> areaOnRight;
	private Box loadedBox;

	public Cart(String name, Rail rail) {
		super(name);
		this.rail = rail;
		this.movementFSM = new ObservableMovementState(MovementState.stop);
		this.loadUnloadFSM = new ObservableLoadUnload(LoadUnloadState.unloaded);
		this.areaOnLeft = new ObservableArea();
		this.areaOnRight = new ObservableArea();
		this.loadedBox = null;
	}

	public Rail getRail() {
		return rail;
	}

	public ObservableInteger getPosition() {
		return position;
	}

	public Float getVelocity() {
		return velocity;
	}

	public void setVelocity(float f) {
		this.velocity = f;
	}

	public ObservableMovementState getMovement() {
		return this.movementFSM;
	}

	public ObservableLoadUnload getLoadUnload() {
		return loadUnloadFSM;
	}

	public Box getBoxOnLeft() {
		if(areaOnLeft.get()!=null)
			return areaOnLeft.get().getBox();
		else
			return null;
	}
	
	public Box getBoxOnRight() {
		if(areaOnRight.get()!=null)
			return areaOnRight.get().getBox();
		else
			return null;
	}
	
	public Observable<Area, AreaRef> getAreaOnLeft() {
		return areaOnLeft;
	}
	
	public Observable<Area, AreaRef> getAreaOnRight() {
		return areaOnRight;
	}
	
	public Box getLoadedBox() {
		return loadedBox;
	}

	public void setLoadedBox(Box loadedBox) {
		this.loadedBox = loadedBox;
	}
	
}
