package unitn.aose.warehousesim.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import unitn.aose.warehousesim.api.data.RailRef;

public class Rail extends RailRef {
	
	protected Map<Integer, Area> leftAreas;
	protected Map<Integer, Area> rightAreas;
	protected Map<Integer, Cross> crosses;
	protected Collection<Cart> carts;
	
	
	
	public Rail(String name, int steps) {
		super(name, steps);
		this.leftAreas = new HashMap<Integer, Area>();
		this.rightAreas = new HashMap<Integer, Area>();
		this.crosses = new HashMap<Integer, Cross>();
		this.carts = new HashSet<Cart>();
	}

	
	
	public void addCart(Cart c) {
		carts.add(c);
	}
	
	public Collection<Cart> getCarts() {
		return carts;
	}
	
	public Cart getCartIn(int i) {
		for(Cart c : carts)
			if(c.getPosition().get()==i)
				return c;
		return null;
	};
	
	
	
	public Map<Integer, Area> getLeftAreas() {
		return leftAreas;
	}
	
	public Map<Integer, Area> getRightAreas() {
		return rightAreas;
	}
	
	public void addLeftArea(Integer i, Area l) {
		leftAreas.put(i, l);
	}
	
	public void addRightArea(Integer i, Area l) {
		rightAreas.put(i, l);
	}
	
	
	
	public void addCross(Integer i, Rail r, int railIndex, boolean directionRightTrueOrLeftFalse) {
		crosses.put(i, new Cross(r, railIndex, directionRightTrueOrLeftFalse));
	}
	
	public Map<Integer, Cross> getCrosses() {
		return crosses;
	}

}
