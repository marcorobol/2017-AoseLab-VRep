package unitn.aose.warehousesim.adapter.vrep;

import java.util.HashMap;
import java.util.Map;

import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.RailRef;
import coppelia.IntW;
import coppelia.remoteApi;

public class RailVRep extends RailRef {
	
	/*
	 * Parameters
	 */
//	final float targetVelocity = 1f;
	float lenght = 7.5f;
	
	@SuppressWarnings("unused")
	private remoteApi vrep;
	@SuppressWarnings("unused")
	private int clientID;
	private String name;
	private IntW handle;
	protected Map<Integer, AreaVRep> leftAreas;
	protected Map<Integer, AreaVRep> rightAreas;
	
	MovementState movementState = MovementState.stop;
	
	
	public RailVRep(remoteApi vrep, int clientID, String name,
			float total_lenght, int steps,
			EnvironmentVRep environmentVRep) {
		super(steps);
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		this.lenght = total_lenght;
		this.leftAreas = new HashMap<Integer, AreaVRep>();
		this.rightAreas = new HashMap<Integer, AreaVRep>();
		/*
		 * Retrive handle
		 */
        this.handle = new IntW(1);
        vrep.simxGetObjectHandle(clientID, name, handle, remoteApi.simx_opmode_blocking);
	}
	
	

	
	public Map<Integer, AreaVRep> getLeftAreas() {
		return leftAreas;
	}
	
	public Map<Integer, AreaVRep> getRightAreas() {
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
	
	public void addLeftArea(Integer i, AreaVRep l) {
		leftAreas.put(i, l);
	}
	
	public void addRightArea(Integer i, AreaVRep l) {
		rightAreas.put(i, l);
	}

}
