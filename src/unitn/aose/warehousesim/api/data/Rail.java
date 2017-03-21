package unitn.aose.warehousesim.api.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rail {
	
	/*
	 * Variabili
	 */
	
	private int lenght;

	private Map<Integer, LandingArea> areas;
	
	/*
	 * Costruttore
	 */
	
	public Rail(int lenght) {
		this.lenght = lenght;
		this.areas = new HashMap<Integer, LandingArea>();
	}
	
	/*
	 * Metodi
	 */
	
	public int getLenght() {
		return lenght;
	}
	
	public void addLandingArea(Integer i, LandingArea l) {
		areas.put(i, l); //Aggiungo una landing area l nella posizione i del binario
	}
	
	public Map<Integer, LandingArea> getAreas() {
		return areas;
	}
	
}
