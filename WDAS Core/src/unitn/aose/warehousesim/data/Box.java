package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.data.BoxRef;


public class Box extends BoxRef {

	private Area area;

	private Cart robot;
	
	
	
	public Box(String name) {
		super(name);
	}
	
	
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Cart getRobot() {
		return robot;
	}

	public void setRobot(Cart robot) {
		this.robot = robot;
	}

	
}