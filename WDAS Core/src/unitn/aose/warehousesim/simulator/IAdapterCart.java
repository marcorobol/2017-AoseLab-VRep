package unitn.aose.warehousesim.simulator;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;

public interface IAdapterCart {
	
	void moveForward();
	void moveBackward();
	void moveTo(Integer index);
	
	void loadBox(BoxRef box, Boolean rightSideOrLeftSide);
	void unloadBoxInArea(BoxRef box, AreaRef area, Boolean rightSideOrLeftSide);
	
}
