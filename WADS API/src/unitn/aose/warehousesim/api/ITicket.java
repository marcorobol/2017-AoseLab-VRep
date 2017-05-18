package unitn.aose.warehousesim.api;

import unitn.aose.warehousesim.api.data.BoxRef;

public interface ITicket {
	
	String getCode();
	
	boolean depositOrWithdrawRequest();
	
	BoxRef getBox();
	
	int getState();
	
}
