package unitn.aose.warehousesim.simulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.IWarehouseMonitor;
import unitn.aose.warehousesim.api.SimulationState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.api.data.StorageAreaRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Cart;
import unitn.aose.warehousesim.data.DepositWithdrawArea;
import unitn.aose.warehousesim.data.Rail;
import unitn.aose.warehousesim.data.StorageArea;
import unitn.aose.warehousesim.observable.ObservableLong;
import unitn.aose.warehousesim.observable.ObservableSimulationState;

public class Warehouse implements IWarehouse {

	private final IAdapter adapter;
	
	private final ObservableSimulationState simulationState;
	private final ObservableLong simulationTime;
	
    private final Map<RailRef, Rail> rails = new HashMap<RailRef, Rail>();
    private final Map<String, AreaRef> areas;
	private final Map<StorageAreaRef, Area> storageAreas = new HashMap<StorageAreaRef, Area>();
	private final Map<DepositWithdrawAreaRef, Area> depositWithdrawAreas = new HashMap<DepositWithdrawAreaRef, Area>();
    private final Map<BoxRef, Box> boxes = new HashMap<BoxRef, Box>();
	private final Map<CartRef, Cart> carts = new HashMap<CartRef, Cart>();
	
	
	
	public Warehouse(IAdapter adapter) {
		this.adapter = adapter;
		areas = new HashMap<String, AreaRef>();
		simulationTime = new ObservableLong();
		simulationTime.set(0l);
		simulationState = new ObservableSimulationState(simulationTime);
		simulationState.set(SimulationState.stopped);
	}
	
	
	
	public IAdapter getAdapter() {
		return adapter;
	}
	
	
	
	/*
	 * Factory
	 */

	public Rail defineRail(String name, int steps) {
		Rail r = new Rail(name, steps);
		rails.put(r, r);
		return r;
	}

	public Cart defineCart(String name, RailRef rail) {
		Cart c = new Cart(name, rails.get(rail), adapter, this);
		rails.get(rail).addCart(c);
		carts.put(c, c);
		return c;
	}

	public Area defineArea(String name) {
		StorageArea a = new StorageArea(name, adapter);
		storageAreas.put(a, a);
		areas.put(name, a);
		return a;
	}

	public Area defineDepositWithdrawArea(String name) {
		DepositWithdrawArea a = new DepositWithdrawArea(name, adapter);
		depositWithdrawAreas.put(a, a);
		areas.put(name, a);
		return a;
	}

	public Box defineBox(String name) {
		Box b = new Box(name);
		boxes.put(b, b);
		return b;
	}
    
    
    
    /*
     * Get given reference
     */

//	public Cart getCart(CartRef ref) {
//		return carts.get(ref);
//	}
//	
//	public Area getArea(AreaRef ref) {
//		return areas.get(ref);
//	}
//	
//	public Box getBox(BoxRef ref) {
//		return boxes.get(ref);
//	}
//
//	public Rail getRail(RailRef ref) {
//		return rails.get(ref);
//	}
	
	
	
	@Override
	public IRobot getRobot(CartRef ref) {
		return carts.get(ref);
	}
	
	/**
	 * Look for a depositwithraw area (tellermachine)
	 * based on its name
	 */
	@Override
	public ITellerMachine getTellerMachine(String areaName){
		ITellerMachine m = null;
		AreaRef ar = areas.get(areaName);
		if(null != ar){
			Area a = depositWithdrawAreas.get(ar);
			m = (a instanceof ITellerMachine) ? (ITellerMachine)a : null; 
		}
		return m;
	}
	
	@Override
	public ITellerMachine getTellerMachine(DepositWithdrawAreaRef area) {
		Area a = depositWithdrawAreas.get(area);
		if(a instanceof DepositWithdrawArea)
			return (DepositWithdrawArea) a;
		else
			return null;
	}
	
	/*
	 * Get arrays
	 */
    
	@Override
	public Set<CartRef> getCarts() {
		return carts.keySet();
	}
	
	@Override
	public Set<StorageAreaRef> getStorageAreas() {
		return storageAreas.keySet();
	}
	
	@Override
	public Set<DepositWithdrawAreaRef> getDepositWithdrawAreas() {
		return depositWithdrawAreas.keySet();
	}
	
	@Override
	public Set<BoxRef> getBoxes() {
		return boxes.keySet();
	}
	
	@Override
	public Set<RailRef> getRails() {
		return rails.keySet();
	}
	
	

	@Override
	public boolean isAStorageArea(AreaRef area) {
		return area instanceof StorageArea;
	}
	
	
	
	/*
	 * Create and delete boxes
	 */
	
	@Override
	public BoxRef createBox(String areaName){
		AreaRef ar = areas.get(areaName);
		return null != ar ? createBox(ar) : null;
	}

	@Override
	public BoxRef createBox(AreaRef areaRef) {
		Area a = storageAreas.get(areaRef);
		if(a==null)
			a = depositWithdrawAreas.get(areaRef);
		if(a!=null)
			return a.createBox();
		return null;
	}

	@Override
	public void deleteBox(BoxRef boxRef) {
		Box box = boxes.get(boxRef);
		Area area = box.getArea();
		Cart cart = box.getCart();
		
		if(area!=null)
			area.removeBox();
		else if(cart!=null)
			cart.removeBox();
		else
			adapter.deleteBox(boxRef);
	}
	
	
	
	/*
	 * Simulation state and time
	 */
	
	@Override
	public IObservable<SimulationState> getSimulationState() {
		return simulationState;
	}
	
	@Override
	public ObservableLong getSimulationTime() {
		return simulationTime;
	}
	
	@Override
	public void play() {
		if(simulationState.get()!=SimulationState.playing) {
			adapter.play();
			simulationState.set(SimulationState.playing);
		}
	}
	
	@Override
	public void pause() {
		if(simulationState.get()==SimulationState.playing) {
			adapter.pause();
			simulationState.set(SimulationState.paused);
		}
	}
	
	@Override
	public void stop() {
		if(simulationState.get()!=SimulationState.stopped) {
			adapter.stop();
			simulationState.set(SimulationState.stopped);
			simulationTime.set(0l);
		}
	}
	
}
