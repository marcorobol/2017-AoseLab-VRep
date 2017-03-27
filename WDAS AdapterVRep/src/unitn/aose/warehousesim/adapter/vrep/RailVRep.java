package unitn.aose.warehousesim.adapter.vrep;

import unitn.aose.warehousesim.data.Rail;
import coppelia.IntW;
import coppelia.remoteApi;

public class RailVRep extends Rail {
	
	/*
	 * Parameters
	 */
//	final float targetVelocity = 1f;
	float lenght = 7.5f;
	
	private remoteApi vrep;
	private int clientID;
	private IntW handle;
	
	
	
	public RailVRep(remoteApi vrep, int clientID, String name,
			float total_lenght, int steps,
			EnvironmentVRep environmentVRep) {
		super(name, steps, environmentVRep);
		this.vrep = vrep;
		this.clientID = clientID;
		this.lenght = total_lenght;
		init();
	}
	
	public void init() {
		/*
		 * Retrive handle
		 */
        this.handle = new IntW(1);
        vrep.simxGetObjectHandle(clientID, getName(), handle, remoteApi.simx_opmode_blocking);
	}
	
	

	public float getLenght() {
		return lenght;
	}
	
	public float getStep() {
		return lenght / getSteps();
	}

}
