package unitn.aose.warehousesim.agent;

import unitn.aose.warehousesim.api.IWarehouse;

/**
 * Provides interface methods to handle the warehouse
 * @author matteo.pedrotti@deltainformatica.eu
 *
 */
public interface IWarehouseAgent {

	/**
	 * The warehouse provides access to teller machines
	 * and associated data.
	 * @return
	 */
	IWarehouse getWarehouse();
}
