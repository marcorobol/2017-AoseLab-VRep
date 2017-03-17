package unitn.aose.warehousesim.robot;

import coppelia.remoteApi;
import unitn.aose.warehousesim.api.data.LandingArea;

public class LandingAreaVRep extends LandingArea {
	
	private remoteApi vrep;
	private int clientID;
	private String name;

	public LandingAreaVRep(remoteApi vrep, int clientID, String name) {
		super();
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
	}
	
	
}
