package unitn.aose.warehousesim.data;

import java.util.HashMap;
import java.util.Map;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.SimulationState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.MovementWithRespectToMe;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;
import unitn.aose.warehousesim.observable.ObservableArea;
import unitn.aose.warehousesim.observable.ObservableCart;
import unitn.aose.warehousesim.observable.ObservableCross;
import unitn.aose.warehousesim.observable.ObservableInteger;
import unitn.aose.warehousesim.observable.ObservableLoadUnload;
import unitn.aose.warehousesim.observable.ObservableMovementState;
import unitn.aose.warehousesim.simulator.IAdapter;


public class Cart implements CartRef, IRobot {

	private final String name;
	private final IAdapter adapter;
	private final IWarehouse warehouse;
	private final Rail rail;
	private final ObservableMovementState movementFSM;
	private final ObservableLoadUnload loadUnloadFSM;
	private final ObservableInteger position;
	private final ObservableArea areaOnLeft;
	private final ObservableArea areaOnRight;
	private final ObservableCross crossAhead;
	private final ObservableCross crossBehind;
	private final ObservableCross crossHere;
	private Box loadedBox;
	private final Map<CartRef, CartPerception> cartPerceptions;
	private final Map<PositionWithRespectToMe, ObservableCart> cartAround;
	
	public Cart(String name, Rail rail, IAdapter adapter, IWarehouse warehouse) {
		this.name= name;
		this.adapter = adapter;
		this.warehouse = warehouse;
		this.rail = rail;
		this.movementFSM = new ObservableMovementState(MovementState.stop);
		this.loadUnloadFSM = new ObservableLoadUnload(LoadUnloadState.unloaded);
		this.position = new ObservableInteger();
		this.areaOnLeft = new ObservableArea();
		this.areaOnRight = new ObservableArea();
		this.crossAhead = new ObservableCross();
		this.crossBehind = new ObservableCross();
		this.crossHere = new ObservableCross();
		this.loadedBox = null;
		this.cartPerceptions = new HashMap<CartRef, CartPerception>();
		this.cartAround = new HashMap<PositionWithRespectToMe, ObservableCart>();
		for(PositionWithRespectToMe p : PositionWithRespectToMe.values()){
			cartAround.put(p, new ObservableCart());
		}
		
		/*
		 * Monitor changes of position and update other
		 */
		position.registerListener(new IListener<Integer>() {
			public void notifyChanged(Integer pos) {
				/*
				 * Check if there is a different area on left and right side
				 */
				getAreaOnLeft().set( getRail().getLeftAreas().get(pos) );
				getAreaOnRight().set( getRail().getRightAreas().get(pos) );
				/*
				 * Check if there is a cross on the next and previous position
				 */
<<<<<<< HEAD
				crossAhead.set( getRail().getCrosses().get(pos+1) );
=======
				System.out.println(getRail()+" @ "+pos+": "+crossHaed.get()+" / "+crossHere.get()+" / "+crossBehind.get());
				crossHaed.set( getRail().getCrosses().get(pos+1) );
>>>>>>> c45522fc6df4926e6d76a522bfe9887d9529103f
				crossBehind.set( getRail().getCrosses().get(pos-1) );
				crossHere.set( getRail().getCrosses().get(pos) );
				System.out.println(name + " @ " + pos + " : Ahead " + crossAhead.get() + " Behind: " + crossBehind.get() + " Here: " + crossHere.get());
				/*
				 * Update perceptions
				 */
				updateOthersPerceptionOfMe();
				updateMyPerceptionsOfOthers();
			}
		});
		
	}
	
	public String getName() {
		return name;
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+"["+this.getName()+"]";
	}
	
	private void updateOthersPerceptionOfMe() {
		for(Cart c : rail.getCarts())
			if(c!=this)
				c.updateMyPerceptionOf(this);
		for(Cross cross : rail.getCrosses().values())
			for(Cart c : cross.getRail().getCarts())
				c.updateMyPerceptionOf(this);
	}
	
	private void updateMyPerceptionsOfOthers() {
		for(Cart c : rail.getCarts())
			if(c!=this)
				updateMyPerceptionOf(c);
		for(Cross cross : rail.getCrosses().values())
			for(Cart c : cross.getRail().getCarts())
				updateMyPerceptionOf(c);
	}
	
	public void updateMyPerceptionOf(Cart c) {
		/*
		 * Compute distance and movement with respect to me
		 */
		final PositionWithRespectToMe pos = computeDistanceWithRespectToMe(c);
		final MovementWithRespectToMe mov = computeMovementWithRespectToMe(c);

		/*
		 * Update cart_around map
		 */
		if(!pos.equals(PositionWithRespectToMe.unknown) && cartAround.containsKey(pos)){
			cartAround.get(pos).set(c);
		}else{
			//look for cart in the cartAround map and remove it
			for(ObservableCart oc : cartAround.values()){
				if(c.equals(oc.get())){
					oc.set(null);
				}
			}
		}

		/*
		 * Update cart_perceptions map
		 */
		CartPerception p = cartPerceptions.get(c);
		if(p==null){
			p = new CartPerception(c, c.getRail());
			cartPerceptions.put(c, p);
		}

		// If it is farer than 1,1 hide it
		if(pos==PositionWithRespectToMe.unknown || pos.getForward()>1 || pos.getLateral()>1) {
			p.getInFieldOfView().set(false);
			p.getPositionWithRespectToMe().set(PositionWithRespectToMe.unknown);
			p.getMovementWithRespectToMe().set(MovementWithRespectToMe.unknown);
		}
		else {
			p.getInFieldOfView().set(true);
			p.getPositionWithRespectToMe().set(pos);
			p.getMovementWithRespectToMe().set(mov);
		}
	}
	
	public MovementWithRespectToMe computeMovementWithRespectToMe(Cart cart) {
		if(null == position || null == position.get() || null == cart.getPosition() || null == cart.getPosition().get()) return MovementWithRespectToMe.unknown;
		int myPosition = position.get();
		int hisPosition = cart.getPosition().get();
		MovementState cartMovement = cart.getMovement().get();
		// if not moving...
		if(cartMovement == MovementState.stop || cartMovement == MovementState.stopping){
			return MovementWithRespectToMe.steady;
		}
		// If on the same rail
		if(cart.getRail()==getRail()){
			if((myPosition>hisPosition && cartMovement == MovementState.runningForward) ||
				(hisPosition>myPosition && cartMovement == MovementState.runningBackward)){
				return MovementWithRespectToMe.gettingCloser;
			} else if((myPosition>hisPosition && cartMovement == MovementState.runningBackward) ||
					(hisPosition>myPosition && cartMovement == MovementState.runningForward)){
				return MovementWithRespectToMe.goingAway;
			}
		}
		// If on another rail, look for the cross intersecting that rail
		for(Integer localIndex : getRail().getCrosses().keySet()) {
			Cross cross = getRail().getCrosses().get(localIndex);
			for(Cart c : cross.getRail().getCarts()){
				if(c==cart){
					if((cross.getRailIndex()>hisPosition && cartMovement == MovementState.runningForward) ||
						(cross.getRailIndex()<hisPosition && cartMovement == MovementState.runningBackward)){
						return MovementWithRespectToMe.gettingCloser;
					} else if((cross.getRailIndex()>hisPosition && cartMovement == MovementState.runningBackward) ||
						(cross.getRailIndex()<hisPosition && cartMovement == MovementState.runningForward)){
						return MovementWithRespectToMe.goingAway;
					} else if(cross.getRailIndex()==hisPosition){
						//special case whenever the other robot is at the cross
						return MovementWithRespectToMe.steady;
					}
				}
			}
		}
		return MovementWithRespectToMe.unknown;
	}
	
	public PositionWithRespectToMe computeDistanceWithRespectToMe(Cart cart) {
		if(null == position || null == position.get() || null == cart.getPosition() || null == cart.getPosition().get()) return PositionWithRespectToMe.unknown;
		int myPosition = position.get();
		int otherPosition = cart.getPosition().get();
		// If on the same rail
		if(cart.getRail()==getRail()){
			PositionWithRespectToMe p = new PositionWithRespectToMe(otherPosition-myPosition, 0);
			return p;
		}
		// If on another rail, look for the cross intersecting that rail
		for(Integer localIndex : rail.getCrosses().keySet()) {
			Cross cross = rail.getCrosses().get(localIndex);
			Integer dir = (cross.isRightTrueOrLeftFalse()?1:-1);
			for(Cart c : cross.getRail().getCarts()){
				if(c==cart){
					PositionWithRespectToMe p = new PositionWithRespectToMe(localIndex-myPosition, (otherPosition-cross.getRailIndex())*dir);
					return p;
				}
			}
		}
		return PositionWithRespectToMe.unknown;
	}

	public Rail getRail() {
		return rail;
	}

	@Override
	public ObservableInteger getPosition() {
		return position;
	}

	@Override
	public ObservableMovementState getMovement() {
		return this.movementFSM;
	}

	@Override
	public ObservableLoadUnload getLoadUnload() {
		return loadUnloadFSM;
	}

	@Override
	public Box getBoxOnLeft() {
		if(areaOnLeft.get()!=null)
			return areaOnLeft.get().getBox();
		else
			return null;
	}

	@Override
	public Box getBoxOnRight() {
		if(areaOnRight.get()!=null)
			return areaOnRight.get().getBox();
		else
			return null;
	}

	@Override
	public ObservableArea getAreaOnLeft() {
		return areaOnLeft;
	}

	@Override
	public ObservableArea getAreaOnRight() {
		return areaOnRight;
	}

	@Override
	public Box getLoadedBox() {
		return loadedBox;
	}

	private void setLoadedBox(Box loadedBox) {
		this.loadedBox = loadedBox;
	}
	
	
	
	@Override
	public IObservable<AreaState> getAreaState(AreaRef area) {
		for(Area a : rail.getLeftAreas().values())
			if(a==area)
				return a.getState();
		for(Area a : rail.getRightAreas().values())
			if(a==area)
				return a.getState();
		return null;
	}

	public boolean isAStorageArea(AreaRef area) {
		return area instanceof StorageArea;
	}
	
	
	/*
	 * Cart perceptions
	 */

	public Map<CartRef, CartPerception> getCartPerceptions() {
		return cartPerceptions;
	}

	@Override
	public CartPerception getCartPerception(CartRef c) {
		return cartPerceptions.get(c);
	}

	@Override
	public ObservableCart getCartAround(PositionWithRespectToMe pos) {
		return cartAround.get(pos);
	}
	
	
	
	/*
	 * Crosses
	 */

	@Override
	public ObservableCross getCrossHaed() {
		return crossAhead;
	}

	@Override
	public ObservableCross getCrossBehind() {
		return crossBehind;
	}

	@Override
	public ObservableCross getCrossHere() {
		return crossHere;
	}
	
	
	
	/*
	 * Controls
	 */

	@Override
	public void moveForward() {
		adapter.getAdapterCart(this).moveForward();
		getMovement().set(MovementState.runningForward);
	}

	@Override
	public void moveBackward() {
		adapter.getAdapterCart(this).moveBackward();
		getMovement().set(MovementState.runningBackward);
	}

	@Override
	public void stopHere() {
		Integer index = getPosition().get();
		if(null != index){
			adapter.getAdapterCart(this).moveTo(index);
			getMovement().set(MovementState.stopping);
		}else{
			//current position not set, cannpt stop (yet)
		}
	}

	@Override
	public void loadRight() {
		Area area = getAreaOnRight().get();
		Box box = getBoxOnRight();
		load(area, box, true);
	}

	@Override
	public void loadLeft() {
		Area area = getAreaOnLeft().get();
		Box box = getBoxOnLeft();
		load(area, box, false);
	}
	
	private void load(Area area, Box box, Boolean rightSideOrLeftSide) {
		if(getLoadedBox()==null && box!=null && getMovement().get()==MovementState.stop) {
			adapter.getAdapterCart(this).loadBox(box, rightSideOrLeftSide);
	        area.setBox(null);
	        setLoadedBox(box);
	        getLoadUnload().set(LoadUnloadState.loaded);
		}
	}

	@Override
	public void unloadLeft() {
		Area area = getAreaOnLeft().get();
		unloadIn(area, false);
	}

	@Override
	public void unloadRight() {
		Area area = getAreaOnRight().get();
		unloadIn(area, false);
	}

	private void unloadIn(Area area, Boolean rightSideOrLeftSide) {
		Box box = getLoadedBox();
		if(loadedBox!=null && area!=null && area.getBox()==null && getMovement().get()==MovementState.stop) {
			adapter.getAdapterCart(this).unloadBoxInArea(box, area, rightSideOrLeftSide);
	        area.setBox(loadedBox);
	        setLoadedBox(null);
	        getLoadUnload().set(LoadUnloadState.unloaded);
		}
	}
	

	public void removeBox() {
		adapter.deleteBox(loadedBox);

		setLoadedBox(null);
		loadedBox.setCart(null);
		loadUnloadFSM.set(LoadUnloadState.unloaded);
	}

	@Override
	public IObservable<Long> getSimulationTime() {
		return warehouse.getSimulationTime();
	}

	@Override
	public IObservable<SimulationState> getSimulationState() {
		return warehouse.getSimulationState();
	}
	
}
