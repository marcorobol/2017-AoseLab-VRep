package unitn.aose.warehousesim;

import java.util.List;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Cart;

public interface IEnvironment {
	
	List<Cart> getRobots();
	
	List<Area> getAreas();
	
	List<Box> getBoxes();
	
	
	
	BoxRef createBoxIn(AreaRef areaRef);
	
	void deleteBoxIn(AreaRef areaRef);

	
	
	void update();
	
}
