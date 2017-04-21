package unitn.aose.warehousesim.simulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.SimulationState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Cart;
import unitn.aose.warehousesim.data.Rail;
import unitn.aose.warehousesim.observable.ObservableLong;
import unitn.aose.warehousesim.observable.ObservableSimulationState;

public class Warehouse implements IWarehouse {

	private final IAdapter adapter;
	
	private final ObservableSimulationState simulationState;
	private final ObservableLong simulationTime;
	
    private final Map<RailRef, Rail> rails = new HashMap<RailRef, Rail>();
	private final Map<AreaRef, Area> areas = new HashMap<AreaRef, Area>();
    private final Map<BoxRef, Box> boxes = new HashMap<BoxRef, Box>();
	private final Map<CartRef, Cart> carts = new HashMap<CartRef, Cart>();
	
	
	
	public Warehouse(IAdapter adapter) {
		this.adapter = adapter;
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
		Area a = new Area(name, adapter);
		areas.put(a, a);
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
	
	@Override
	public ITellerMachine getTellerMachine(AreaRef area) {
		return areas.get(area);
	}
	
	

	
	/*
	 * Get arrays
	 */
    
	@Override
	public Set<CartRef> getCarts() {
		return carts.keySet();
	}
	
	@Override
	public Set<AreaRef> getAreas() {
		return areas.keySet();
	}
	
	@Override
	public Set<BoxRef> getBoxes() {
		return boxes.keySet();
	}
	
	@Override
	public Set<RailRef> getRails() {
		return rails.keySet();
	}
	
	
	
	/*
	 * Create and delete boxes
	 */

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
