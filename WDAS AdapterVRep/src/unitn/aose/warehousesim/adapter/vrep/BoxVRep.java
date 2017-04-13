package unitn.aose.warehousesim.adapter.vrep;

import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.data.Box;

public class BoxVRep {

	private final remoteApi vrep;
	private final int clientID;
	private final Box box;
	
	private IntW handle;
	private boolean deleted = false;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public BoxVRep(remoteApi vrep, int clientID, Box box) {
		this.vrep = vrep;
		this.clientID = clientID;
		this.box = box;
		init();
	}
	
	public void init() {
		/*
		 * Retrive handle
		 */
		handle = new IntW(0);
        int r = vrep.simxGetObjectHandle(clientID, box.getName(), handle, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+box.getName()+", error : "+r);
        }
	}
	
	public IntW getHandle() {
		return handle;
	}
	
}
