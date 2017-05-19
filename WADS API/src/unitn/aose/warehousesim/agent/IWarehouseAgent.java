package unitn.aose.warehousesim.agent;

import java.util.Collection;

import unitn.aose.warehousesim.api.IWarehouseMonitor;

/**
 * Provides interface methods to handle the warehouse
 * @author matteo.pedrotti@deltainformatica.eu
 *
 */
public interface IWarehouseAgent extends ISimulationAgent {

	/**
	 * The warehouse provides access to teller machines
	 * and associated data.
	 * @return
	 */
	IWarehouseMonitor getWarehouse();
	
	/**
	 * The agent is notified when a request is made from one of the teller machines
	 * associated to the depositWithdraw areas of the warehouse
	 * @param ticketCode
	 * @param deposit if this is a deposit or withdraw request
	 * @param areaNameAssociatedToTheTM
	 * @param boxName
	 */
	void handleRequest(String ticketCode, boolean deposit, String areaNameAssociatedToTheTM, String boxName);
	
	/**
	 * Tell this coordinate the agents available
	 * @param robotAgents
	 */
	void coordinate(Collection<IRobotAgent> robotAgents);
}
