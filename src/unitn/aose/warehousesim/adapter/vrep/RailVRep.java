package unitn.aose.warehousesim.adapter.vrep;

import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;

public class RailVRep extends Rail {
	
	/*
	 * Parameters
	 */
	final float targetVelocity = 1f;
	final float lenght = 7.5f;
	
	private remoteApi vrep;
	private int clientID;
	private String name;
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
	
	public void addLeftArea(Integer i, LandingArea l) {
		leftAreas.put(i, l);
	}
	
	public void addRightArea(Integer i, LandingArea l) {
		rightAreas.put(i, l);
	}

}
