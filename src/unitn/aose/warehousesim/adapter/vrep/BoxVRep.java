package unitn.aose.warehousesim.adapter.vrep;

import unitn.aose.warehousesim.api.data.Box;
import coppelia.IntW;
import coppelia.remoteApi;

public class BoxVRep extends Box {

	private remoteApi vrep;
	private int clientID;
	private String name;
	private IntW handle;
	
	public BoxVRep(remoteApi vrep, int clientID, String name) {
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		
		/*
		 * Retrive handle
		 */
		handle = new IntW(0);
        int r = vrep.simxGetObjectHandle(clientID, name, handle, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+name+", error : "+r);
        }
	}
	
	public IntW getHandle() {
		return handle;
	}
	
//	public void moveOnRobot(RobotVRep robot) {
//		int r = vrep.simxSetObjectPosition(clientID, handle, -1, robot.getBoxPosition(), remoteApi.simx_opmode_streaming);
//        if(r!=vrep.simx_return_ok) {
//        	System.out.println("ERROR Retriving handle of "+name+", error : "+r);
//        }
//	}
	
	

}
