package unitn.aose.warehousesim.agent;

import java.util.Collection;

import unitn.aose.warehousesim.api.IWarehouseMonitor;
import unitn.aose.warehousesim.api.data.Ticket;

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
	 * I think this method could became useless if we pass the list of the available agents
	 * directly in the handleRequest. What do you think about this?
	 * @param agents
	 */
	void coordinate(Collection<IRobotAgent> agents);
	
	/**
	 * The agent is notified when a request is made from one of the teller machines
	 * associated to the depositWithdraw areas of the warehouse
	 * @param ticket is automatically generated by the teller machine and it is associated to the request
	 * @param agents is the list of agents that the coordinator can use to handle the request
	 */
	void handleRequest(Ticket ticket, Collection<IRobotAgent> agents);
}
