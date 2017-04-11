package unitn.aose.warehousesim.simulator;

import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.data.Box;

public interface IAdapter {
	
	IAdapterCart getAdapterCart(CartRef cart);
	
	
	
	Box createBoxIn(AreaRef areaRef);
	
	void deleteBox(BoxRef boxRef);
	
	
	
	void synchronousTrigger();
	
	void update(IWarehouse warehouse);
	
	void play();
	
	void pause();
	
	void stop();
	
	long getSeverTime();
	
}
