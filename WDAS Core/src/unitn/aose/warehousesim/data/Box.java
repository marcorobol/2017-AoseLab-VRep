package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.data.BoxRef;


public class Box implements BoxRef {

	private final String name;
	
	private Area area;

	private Cart robot;
	
	
	
	public Box(String name) {
		this.name = name;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Cart getCart() {
		return robot;
	}

	public void setCart(Cart robot) {
		this.robot = robot;
	}

	
}
