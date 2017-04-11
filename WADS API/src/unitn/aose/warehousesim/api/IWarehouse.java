package unitn.aose.warehousesim.api;

import java.util.Set;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.RailRef;

public interface IWarehouse {
	
	Set<CartRef> getCarts();
	
	Set<AreaRef> getAreas();

	Set<BoxRef> getBoxes();

	Set<RailRef> getRails();
	
	
	
	IRobot getRobot(CartRef cart);
	
	ITellerMachine getTellerMachine(AreaRef area);
	
	
	
	void deleteBox(BoxRef box);
	
	
	
	IObservable<SimulationState> getSimulationState();

	IObservable<Long> getSimulationTime();
	
	void play();
	
	void pause();
	
	void stop();
	
}
