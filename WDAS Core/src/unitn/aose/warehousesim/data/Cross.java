package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.data.CrossRef;

public class Cross extends CrossRef {
	
	private Rail rail;
	private int railIndex;
	private boolean rightTrueOrLeftFalse;
	
	
	
	public Cross(Rail r, int railIndex, boolean rightTrueOrLeftFalse) {
		this.rail = r;
		this.railIndex = railIndex;
		this.rightTrueOrLeftFalse = rightTrueOrLeftFalse;
	}
	
	
	
	public boolean isRightTrueOrLeftFalse() {
		return rightTrueOrLeftFalse;
	}
	
	public Rail getRail() {
		return rail;
	}
	
	public int getRailIndex() {
		return railIndex;
	}
	
}
