package unitn.aose.warehousesim.api;

import java.util.Set;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.api.data.StorageAreaRef;

public interface IWarehouse extends IWarehouseMonitor {
	
	Set<CartRef> getCarts();
	
	Set<StorageAreaRef> getStorageAreas();
	
	Set<DepositWithdrawAreaRef> getDepositWithdrawAreas();

	Set<BoxRef> getBoxes();

	Set<RailRef> getRails();
	
	
	
	IRobot getRobot(CartRef cart);
	
	ITellerMachine getTellerMachine(DepositWithdrawAreaRef area);
	
	
	
	BoxRef createBox(AreaRef area);
	
	BoxRef createBox(String areaName);
	
	void deleteBox(BoxRef box);
	
	
	
	IObservable<SimulationState> getSimulationState();

	IObservable<Long> getSimulationTime();
	
	void play();
	
	void pause();
	
	void stop();
	
}
