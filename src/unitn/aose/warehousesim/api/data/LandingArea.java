package unitn.aose.warehousesim.api.data;

import java.util.HashMap;

public class LandingArea {
	
	private int landingIndex;
	private Box box;
	
	public LandingArea(int index) {
		this.landingIndex = index;
	}
	
	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
	
	public int getLandinIndex(){
		return landingIndex;
	}
	
}
