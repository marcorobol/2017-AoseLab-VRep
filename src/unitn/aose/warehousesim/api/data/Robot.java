package unitn.aose.warehousesim.api.data;

public class Robot {
	
	private Rail rail;
	
	private Integer position;

	public Robot(Rail r) {
		this.rail = r;
	}
	
	public Rail getRail() {
		return rail;
	}

	public void setRail(Rail rail) {
		this.rail = rail;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
}
