package unitn.aose.warehousesim.api.data;


public class Robot {

	private String name;
	private Rail rail;

	public Robot(String name, Rail rail) {
		this.name = name;
		this.rail = rail;
	}

	public Rail getRail() {
		return rail;
	}

	public String getName() {
		return name;
	}
	
}
