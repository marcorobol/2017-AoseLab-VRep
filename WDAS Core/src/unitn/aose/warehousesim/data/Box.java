package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.data.BoxRef;


public class Box implements BoxRef {

	private final String name;
	private final String stringFormat;
	
	private Area area;

	private Cart robot;
	
	public Box(String name) {
		this.name = name;
		stringFormat = this.getClass().getSimpleName()+"["+name+"]";
	}
	
	public String getName() {
		return name;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
		if(null != area){
			this.robot = null;
		}
	}

	public Cart getCart() {
		return robot;
	}

	public void setCart(Cart robot) {
		this.robot = robot;
		if(null != robot){
			this.area = null;
		}
	}

	@Override
	public String toString(){
		return stringFormat;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof BoxRef){
			return ((BoxRef)obj).getName().equals(name);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}
}
