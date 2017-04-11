package unitn.aose.warehousesim.adapter.vrep;

import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.data.Rail;

public class RailVRep {
	
	private final float step_lenght = 0.5f;
	
	private final remoteApi vrep;
	private final int clientID;
	private final Rail rail;
	private final String railName;
	
	private IntW handle;
	
	
	
	public RailVRep(remoteApi vrep, int clientID, Rail rail, AdapterVRep adapter) {
		this.vrep = vrep;
		this.clientID = clientID;
		this.rail = rail;
		this.railName = rail.getName();
		init();
	}
	
	public void init() {
		/*
		 * Retrive handle
		 */
        this.handle = new IntW(1);
        vrep.simxGetObjectHandle(clientID, railName, handle, remoteApi.simx_opmode_blocking);
	}
	
	

	public float getStepLenght() {
		return step_lenght;
	}

}
