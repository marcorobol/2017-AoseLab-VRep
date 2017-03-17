package unitn.aose.warehousesim.robot;

import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.Rail;

public class RailVRep extends Rail {
	
	/*
	 * Parameters
	 */
	final float targetVelocity = 1f;
	final float lenght = 7.5f;
	
	private remoteApi vrep;
	private String name;
	private int clientID;
	private IntW handle;
	

	MovementState movementState = MovementState.stop;
	
	
	public RailVRep(remoteApi vrep, int clientID, String name, Integer lenght) {
		super(lenght);
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		/*
		 * Retrive handle
		 */
        this.handle = new IntW(1);
        vrep.simxGetObjectHandle(clientID, name, handle, remoteApi.simx_opmode_blocking);
	}
	

	public String getName() {
		return name;
	}

}
