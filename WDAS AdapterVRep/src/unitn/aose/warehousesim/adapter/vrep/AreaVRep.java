package unitn.aose.warehousesim.adapter.vrep;

import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.Logger;
import unitn.aose.warehousesim.data.Area;

public class AreaVRep {
	
	private final remoteApi vrep;
	private final int clientID;
	private final Area area;
	
	private IntW areaH;
	private FloatWA position = new FloatWA(3);
	
	
	public AreaVRep(remoteApi vrep, int clientID, Area area, AdapterVRep environmentVRep) {
		this.vrep = vrep;
		this.clientID = clientID;
		this.area = area;
		init();
	}
	
	public void init() {
		/*
		 * Retrieve handle
		 */
		areaH = new IntW(0);
        int r = vrep.simxGetObjectHandle(clientID, area.getName(), areaH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	Logger.err.println("Retriving handle of "+area.getName()+", error : "+r);
        }
        
        /*
         * Read position
         */
		r = vrep.simxGetObjectPosition(clientID, areaH.getValue(), -1, position, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	Logger.err.println("Retriving area position "+area.getName()+", error : "+r);
        }
	}
	

	public FloatWA getPosition() {
		return position;
	}
	
	public IntW getHandle() {
		return areaH;
	}

}
