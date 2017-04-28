package unitn.aose.warehousesim.data;

import unitn.aose.warehousesim.api.data.StorageAreaRef;
import unitn.aose.warehousesim.simulator.IAdapter;


public class StorageArea extends Area implements StorageAreaRef {
	
	public StorageArea(String name, IAdapter adapter) {
		super(name, adapter);
	}
	
}
