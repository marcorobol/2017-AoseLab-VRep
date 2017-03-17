package unitn.aose.warehousesim.api.data;

public class Robot {
	
	private Rail rail;
	
	protected Integer position = 0;

	protected Float velocity = 0f;


	public Robot(Rail rail) {
		this.rail = rail;
	}
	
	
	public Rail getRail() {
		return rail;
	}
	
	public Integer getPosition() {
		return position;
	}
	
	public Float getVelocity() {
		return velocity;
	}
	
}
