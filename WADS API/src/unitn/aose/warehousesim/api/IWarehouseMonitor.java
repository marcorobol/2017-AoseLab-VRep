package unitn.aose.warehousesim.api;

import java.util.Set;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.api.data.StorageAreaRef;

public interface IWarehouseMonitor {

	Set<CartRef> getCarts();
	
	Set<StorageAreaRef> getStorageAreas();
	
	Set<DepositWithdrawAreaRef> getDepositWithdrawAreas();

	Set<BoxRef> getBoxes();

	Set<RailRef> getRails();
	
	
	
	boolean isAStorageArea(AreaRef area);
	
	
	
	IRobotMonitor getRobot(CartRef cart);
	
	ITellerMachineMonitor getTellerMachine(DepositWithdrawAreaRef area);
	
	
	
	IObservable<SimulationState> getSimulationState();

	IObservable<Long> getSimulationTime();
}
