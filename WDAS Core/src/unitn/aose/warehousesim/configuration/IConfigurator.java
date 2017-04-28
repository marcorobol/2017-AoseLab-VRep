package unitn.aose.warehousesim.configuration;

import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Cart;
import unitn.aose.warehousesim.data.Rail;

public interface IConfigurator {
	
//	public Warehouse getWarehouse();
//	
//	public IAdapter getAdapter();
	
	public Rail defineRail(String name, int steps);
	
	public Cart defineCart(String name, Rail rail);
	
	public Area defineArea(String name);

	public Area defineDepositWithdrawArea(String name);
	
	public Box defineBox(String name);
	
}
