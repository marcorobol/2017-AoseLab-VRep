package unitn.aose.warehousesim.data;

import java.util.HashMap;
import java.util.Map;

import unitn.aose.warehousesim.IEnvironment;
import unitn.aose.warehousesim.api.data.RailRef;

public class Rail extends RailRef {
	
	private String name;
	protected Map<Integer, Area> leftAreas;
	protected Map<Integer, Area> rightAreas;
	
	
	
	public Rail(String name, int steps) {
		super(name, steps);
		this.name = name;
		this.leftAreas = new HashMap<Integer, Area>();
		this.rightAreas = new HashMap<Integer, Area>();
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

}
