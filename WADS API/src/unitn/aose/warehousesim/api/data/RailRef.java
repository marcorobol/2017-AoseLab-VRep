package unitn.aose.warehousesim.api.data;

public interface RailRef {

	String getName();
	
	int getSteps();
	
	Integer[] getLeftAreaPositions();
	
	Integer[] getRightAreaPositions();
	
	AreaRef getLeftAreaIn(int position);
	
	AreaRef getRightAreaIn(int position);
	
	Integer[] getCrossPositions();
	
	RailRef getCrossedRailAt(int position);
	
}
