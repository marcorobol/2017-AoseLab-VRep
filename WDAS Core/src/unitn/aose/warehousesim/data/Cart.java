package unitn.aose.warehousesim.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Locale.Category;

import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.MovementWithRespectToMe;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;
import unitn.aose.warehousesim.observable.Observable;
import unitn.aose.warehousesim.observable.ObservableArea;
import unitn.aose.warehousesim.observable.ObservableCart;
import unitn.aose.warehousesim.observable.ObservableCartPerception;
import unitn.aose.warehousesim.observable.ObservableCross;
import unitn.aose.warehousesim.observable.ObservableInteger;
import unitn.aose.warehousesim.observable.ObservableLoadUnload;
import unitn.aose.warehousesim.observable.ObservableMovementState;


public class Cart extends CartRef {

	private Rail rail;
	private ObservableMovementState movementFSM;
	private ObservableLoadUnload loadUnloadFSM;
	private ObservableInteger position;
	private Float velocity;
	private ObservableArea areaOnLeft;
	private ObservableArea areaOnRight;
	private ObservableCross crossInFront;
	private ObservableCross crossBehind;
	private Box loadedBox;
	private Map<CartRef, CartPerception> cartPerceptions;
	private Map<PositionWithRespectToMe, ObservableCart> cartAround;
	
	public Cart(String name, Rail rail) {
		super(name);
		this.rail = rail;
		this.movementFSM = new ObservableMovementState(MovementState.stop);
		this.loadUnloadFSM = new ObservableLoadUnload(LoadUnloadState.unloaded);
		this.position = new ObservableInteger();
		this.velocity = 0f;
		this.areaOnLeft = new ObservableArea();
		this.areaOnRight = new ObservableArea();
		this.crossInFront = new ObservableCross();
		this.crossBehind = new ObservableCross();
		this.loadedBox = null;
		this.cartPerceptions = new HashMap<CartRef, CartPerception>();
		this.cartAround = new HashMap<PositionWithRespectToMe, ObservableCart>();
		for(PositionWithRespectToMe p : PositionWithRespectToMe.values())
			cartAround.put(p, new ObservableCart());
		
		/*
		 * Monitor changes of position and update other
		 */
		position.registerListener(new IListener<Integer>() {
			public void notifyChanged(Integer value) {
				/*
				 * Check if there is a different area on left and right side
				 */
				getAreaOnLeft().set( getRail().getLeftAreas().get(getPosition().get()) );
				getAreaOnRight().set( getRail().getRightAreas().get(getPosition().get()) );
				/*
				 * Check if there is a cross on the next and previous position
				 */
				crossInFront.set( getRail().getCrosses().get(getPosition().get()+1) );
				crossBehind.set( getRail().getCrosses().get(getPosition().get()-1) );
				/*
				 * Update perceptions
				 */
				updatePerceptions();
			}
		});
		
	}
	
	private void updatePerceptions() {
		for(Cart c : rail.getCarts())
			updatePerception(c);
	}
	
	private void updatePerception(Cart c) {
		/*
		 * Compute distance and movement with respect to me
		 */
		PositionWithRespectToMe pos = computeDistanceWithRespectToMe(c);
		MovementWithRespectToMe mov = computeMovementWithRespectToMe(c);
		
		/*
		 * Update cart_around map
		 */
		cartAround.get(pos).set(c);
		
		/*
		 * Update cart_perceptions map
		 */
		CartPerception p = cartPerceptions.get(c);
		if(p==null){
			p = new CartPerception(c, c.getRail());
			cartPerceptions.put(c, p);
		}
		
		// If it is farer than 1,1 hide it
		if(pos.getForward()>1 || pos.getLateral()>1) {
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
		int myPosition = getPosition().get();
		int hisPosition = cart.getPosition().get();
		// If on the same rail
		if(cart.getRail()==getRail())
			if(myPosition>hisPosition == cart.getMovement().equals(MovementState.runningForward))
				return MovementWithRespectToMe.gettingCloser;
			else
				return MovementWithRespectToMe.goingAway;
		// If on another rail, look for the cross intersecting that rail
		for(Integer localIndex : getRail().getCrosses().keySet()) {
			Cross cross = getRail().getCrosses().get(localIndex);
			for(Cart c : cross.getRail().getCarts())
				if(c==cart)
					if(cross.getRailIndex()>hisPosition == cart.getMovement().equals(MovementState.runningForward))
						return MovementWithRespectToMe.gettingCloser;
					else
						return MovementWithRespectToMe.goingAway;
		}
		return MovementWithRespectToMe.unknown;
	}
	
	public PositionWithRespectToMe computeDistanceWithRespectToMe(Cart cart) {
		int myPosition = getPosition().get();
		int otherPosition = cart.getPosition().get();
		// If on the same rail
		if(cart.getRail()==getRail())
			return new PositionWithRespectToMe(otherPosition-myPosition, 0);
		// If on another rail, look for the cross intersecting that rail
		for(Integer localIndex : getRail().getCrosses().keySet()) {
			Cross cross = getRail().getCrosses().get(localIndex);
			Integer dir = (cross.isRightTrueOrLeftFalse()?1:-1);
			for(Cart c : cross.getRail().getCarts())
				if(c==cart)
					return new PositionWithRespectToMe(localIndex-myPosition, (otherPosition-cross.getRailIndex())*dir);
		}
		return PositionWithRespectToMe.unknown;
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
	
	
	
	/*
	 * Cart perceptions
	 */

	public Map<CartRef, CartPerception> getCartPerceptions() {
		return cartPerceptions;
	}

	public CartPerception getCartPerception(CartRef c) {
		return cartPerceptions.get(c);
	}
	
	public ObservableCart getCartAround(PositionWithRespectToMe pos) {
		return cartAround.get(pos);
	}

}
