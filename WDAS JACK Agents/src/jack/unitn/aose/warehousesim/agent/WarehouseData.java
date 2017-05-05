package unitn.aose.warehousesim.agent;

import java.util.Observable;

import unitn.aose.warehousesim.api.IWarehouseMonitor;

/**
 * Wrapper for the Warehouse
 * @author matteo.pedrotti@deltainformatica.eu
 *
 */
public class WarehouseData extends Observable {

	private IWarehouseMonitor warehouse;
	
	public WarehouseData(){
		
	}
	
	public void setWarehouse(IWarehouseMonitor warehouse){
		this.warehouse = warehouse;
	}
	
	public IWarehouseMonitor getWarehouse(){
		return warehouse;
	}
}
