package unitn.aose.warehousesim.api.data;

public class RailRef {

	/*
	 * Variabili
	 */

	private String name;
	private int steps;
	
	/*
	 * Costruttore
	 */
	
	public RailRef(String name, int steps) {
		this.name = name;
		this.steps = steps;
	}
	
	/*
	 * Metodi
	 */

	public String getName() {
		return name;
	}
	
	public int getSteps() {
		return steps;
	}
	
}
