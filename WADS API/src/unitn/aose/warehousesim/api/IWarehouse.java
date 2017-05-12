package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;

public interface IWarehouse extends IWarehouseMonitor {

	BoxRef createBox(AreaRef area);
	
	BoxRef createBox(String areaName);
	
	void deleteBox(BoxRef box);
	
	int getTicket(String box);
	
	void assignTicket(String box, int ticket);
	
	IObservable<SimulationState> getSimulationState();

	IObservable<Long> getSimulationTime();
	
	IRobot getRobot(CartRef cartRef);
	
	void play();
	
	void pause();
	
	void stop();
	
}
