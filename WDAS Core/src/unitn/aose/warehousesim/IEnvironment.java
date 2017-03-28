package unitn.aose.warehousesim;

import java.util.Set;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;

public interface IEnvironment {
	
	Set<CartRef> getCarts();
	
	Set<AreaRef> getAreas();
	
	Set<BoxRef> getBoxes();
	
	
	
	IRobot getRobot(CartRef cart);
	
	ITellerMachine getTellerMachine(AreaRef area);
	
	
	
	BoxRef createBoxIn(AreaRef areaRef);
	
	void deleteBoxIn(AreaRef areaRef);
	
	
	
	void update();
	
}
