package unitn.aose.warehousesim.api.graphicEngine;

public interface EnvironmentConfiguration {
	
	void registerRail(String name, float[] start, int[] end);
	
	void registerStorageLocation();
	
	void registerBox(String name);
	
	void registerCart(String name, int current, int min, int max);
	
}
