package unitn.aose.warehousesim;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;

public interface IEnvironment {
	
	CartRef[] getCarts();
	
	AreaRef[] getAreas();
	
	BoxRef[] getBoxes();
	
	
	
	IRobot getRobot(CartRef cart);
	
	ITellerMachine getTellerMachine(AreaRef area);
	
	
	
	BoxRef createBoxIn(AreaRef areaRef);
	
	void deleteBoxIn(AreaRef areaRef);
	
	
	
	void update();
	
}
