package unitn.aose.warehousesim.simulator;

import unitn.aose.warehousesim.api.IWarehouse;

public class AdapterUpdateCycle implements Runnable {

	private IAdapter adapter;
	private IWarehouse warehouse;
	
	public AdapterUpdateCycle(IAdapter adapter, IWarehouse warehouse) {
		this.adapter = adapter;
		this.warehouse = warehouse;
	}

	@Override
	public void run() {
		
        long ms = System.currentTimeMillis();
        while(true) {
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	System.out.println(System.currentTimeMillis()-ms+" ms");
        	ms = System.currentTimeMillis();
        	adapter.update(warehouse);
        }
        
	}

}
