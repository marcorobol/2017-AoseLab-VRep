package unitn.aose.warehousesim.api;

import java.util.Set;

import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.DepositWithdrawAreaRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.api.data.StorageAreaRef;

public interface IWarehouseMonitor {

	Set<StorageAreaRef> getStorageAreas();
	
	Set<DepositWithdrawAreaRef> getDepositWithdrawAreas();

	Set<BoxRef> getBoxes();

	Set<RailRef> getRails();

	Set<CartRef> getCarts();
	
	IRobotMonitor getRobot(CartRef cart);
	
	/**
	 * Retrieve a generic area identified by the given name
	 * @param areaName
	 * @return
	 */
	AreaRef getArea(String areaName);
	
	/**
	 * Retrieve the special area "teller machine" identified by the given name
	 * @param areaName
	 * @return
	 */
	ITellerMachine getTellerMachine(String areaName);
	
	ITellerMachine getTellerMachine(DepositWithdrawAreaRef area);
	
	boolean isAStorageArea(AreaRef area);
	
	IObservable<SimulationState> getSimulationState();

	IObservable<Long> getSimulationTime();
}
