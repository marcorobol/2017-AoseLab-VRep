package unitn.aose.warehousesim.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.RailRef;

public class Rail implements RailRef {

	private String name;
	private int steps;
	private Map<Integer, Area> leftAreas;
	private Map<Integer, Area> rightAreas;
	private Map<Integer, Cross> crosses;
	private Collection<Cart> carts;
	
	
	
	public Rail(String name, int steps) {
		this.name = name;
		this.steps = steps;
		this.leftAreas = new HashMap<Integer, Area>();
		this.rightAreas = new HashMap<Integer, Area>();
		this.crosses = new HashMap<Integer, Cross>();
		this.carts = new HashSet<Cart>();
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public int getSteps() {
		return steps;
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
	
	
	
	@Override
	public Integer[] getLeftAreaPositions() {
		return (Integer[]) leftAreas.keySet().toArray();
	}

	@Override
	public Integer[] getRightAreaPositions() {
		return (Integer[]) rightAreas.keySet().toArray();
	}

	@Override
	public AreaRef getLeftAreaIn(int position) {
		return leftAreas.get(position);
	}

	@Override
	public AreaRef getRightAreaIn(int position) {
		return rightAreas.get(position);
	}
	
	
	
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
