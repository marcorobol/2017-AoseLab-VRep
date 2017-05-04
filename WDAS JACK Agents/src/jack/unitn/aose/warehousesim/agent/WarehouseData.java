package unitn.aose.warehousesim.agent;

import java.util.Observable;

import unitn.aose.warehousesim.api.IWarehouse;

/**
 * Wrapper for the Warehouse
 * @author matteo.pedrotti@deltainformatica.eu
 *
 */
public class WarehouseData extends Observable {

	private IWarehouse warehouse;
	
	public WarehouseData(){
		
	}
	
	public void setWarehouse(IWarehouse warehouse){
		this.warehouse = warehouse;
	}
	
	public IWarehouse getWarehouse(){
		return warehouse;
	}
}
