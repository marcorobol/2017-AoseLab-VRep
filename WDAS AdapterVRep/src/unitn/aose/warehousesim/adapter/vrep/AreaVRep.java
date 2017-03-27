package unitn.aose.warehousesim.adapter.vrep;

import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.data.Area;

public class AreaVRep extends Area {
	
	private remoteApi vrep;
	private int clientID;
	
	private IntW areaH;
	private FloatWA position = new FloatWA(3);
	
	
	public AreaVRep(remoteApi vrep, int clientID, String name, EnvironmentVRep environmentVRep) {
		super(name, environmentVRep);
		this.vrep = vrep;
		this.clientID = clientID;
		init();
	}
	
	public void init() {
		/*
		 * Retrieve handle
		 */
		areaH = new IntW(0);
        int r = vrep.simxGetObjectHandle(clientID, getName(), areaH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+getName()+", error : "+r);
        }
        
        /*
         * Read position
         */
		r = vrep.simxGetObjectPosition(clientID, areaH.getValue(), -1, position, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving area position "+getName()+", error : "+r);
        }
	}
	

	public FloatWA getPosition() {
		return position;
	}
	
	public IntW getHandle() {
		return areaH;
	}

}
