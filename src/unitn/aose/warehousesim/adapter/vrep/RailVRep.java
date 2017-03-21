package unitn.aose.warehousesim.adapter.vrep;

import java.util.HashMap;
import java.util.Map;

import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;

public class RailVRep extends Rail {
	
	/*
	 * Parameters
	 */
//	final float targetVelocity = 1f;
	float lenght = 7.5f;
	
	private remoteApi vrep;
	private int clientID;
	private String name;
	private IntW handle;
	protected Map<Integer, LandingAreaVRep> leftAreas;
	protected Map<Integer, LandingAreaVRep> rightAreas;
	
	MovementState movementState = MovementState.stop;
	
	
	public RailVRep(remoteApi vrep, int clientID, String name,
			float total_lenght, int steps,
			EnvironmentVRep environmentVRep) {
		super(steps);
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		this.lenght = total_lenght;
		this.leftAreas = new HashMap<Integer, LandingAreaVRep>();
		this.rightAreas = new HashMap<Integer, LandingAreaVRep>();
		/*
		 * Retrive handle
		 */
        this.handle = new IntW(1);
        vrep.simxGetObjectHandle(clientID, name, handle, remoteApi.simx_opmode_blocking);
	}
	
	

	
	public Map<Integer, LandingAreaVRep> getLeftAreas() {
		return leftAreas;
	}
	
	public Map<Integer, LandingAreaVRep> getRightAreas() {
		return rightAreas;
	}

	public float getLenght() {
		return lenght;
	}
	
	public float getStep() {
		return lenght / getSteps();
	}
	
	public String getName() {
		return name;
	}
	
	public void addLeftArea(Integer i, LandingAreaVRep l) {
		leftAreas.put(i, l);
	}
	
	public void addRightArea(Integer i, LandingAreaVRep l) {
		rightAreas.put(i, l);
	}

}
