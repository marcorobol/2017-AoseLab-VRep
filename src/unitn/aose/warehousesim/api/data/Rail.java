package unitn.aose.warehousesim.api.data;

import java.util.HashMap;
import java.util.Map;

public class Rail {
	
	/*
	 * Variabili
	 */
	
	private int lenght;
	
	protected Map<Integer, LandingArea> leftAreas;
	
	protected Map<Integer, LandingArea> rightAreas;
	
	/*
	 * Costruttore
	 */
	
	public Rail(int lenght) {
		this.lenght = lenght;
		this.leftAreas = new HashMap<Integer, LandingArea>();
		this.rightAreas = new HashMap<Integer, LandingArea>();
	}
	
	/*
	 * Metodi
	 */
	
	public int getLenght() {
		return lenght;
	}
	
	public Map<Integer, LandingArea> getLeftAreas() {
		return leftAreas;
	}
	
	public Map<Integer, LandingArea> getRightAreas() {
		return rightAreas;
	}
	
}
