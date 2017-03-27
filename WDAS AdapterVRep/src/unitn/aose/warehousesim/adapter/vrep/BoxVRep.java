package unitn.aose.warehousesim.adapter.vrep;

import unitn.aose.warehousesim.data.Box;
import coppelia.IntW;
import coppelia.remoteApi;

public class BoxVRep extends Box {

	@SuppressWarnings("unused")
	private remoteApi vrep;
	@SuppressWarnings("unused")
	private int clientID;
	
	private IntW handle;
	
	public BoxVRep(remoteApi vrep, int clientID, String name) {
		super(name);
		this.vrep = vrep;
		this.clientID = clientID;
		
		/*
		 * Retrive handle
		 */
		handle = new IntW(0);
        int r = vrep.simxGetObjectHandle(clientID, name, handle, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+name+", error : "+r);
        }
	}
	
	public IntW getHandle() {
		return handle;
	}
	
}
