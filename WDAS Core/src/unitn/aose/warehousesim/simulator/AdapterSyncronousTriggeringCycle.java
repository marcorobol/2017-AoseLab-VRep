package unitn.aose.warehousesim.simulator;

import unitn.aose.warehousesim.api.SimulationState;

public class AdapterSyncronousTriggeringCycle implements Runnable {

	private IAdapter adapter;
	private Warehouse warehouse;
	
	public AdapterSyncronousTriggeringCycle(IAdapter adapter, Warehouse warehouse) {
		this.adapter = adapter;
		this.warehouse = warehouse;
	}

	@Override
	public void run() {
		
        while(true) {
        	if(warehouse.getSimulationState().get()==SimulationState.playing) {
        		warehouse.getSimulationTime().set(warehouse.getSimulationTime().get() + 50);
//        		long t1 = adapter.getSeverTime();
        		adapter.synchronousTrigger();
//        		long t2 = adapter.getSeverTime();
        	}
			else
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        }
        
	}

}
