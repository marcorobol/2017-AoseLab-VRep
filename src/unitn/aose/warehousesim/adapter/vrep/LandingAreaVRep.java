package unitn.aose.warehousesim.adapter.vrep;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;

import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.data.LandingArea;

public class LandingAreaVRep extends LandingArea {
	
	private remoteApi vrep;
	private int clientID;
	private String name;

	private IntW areaH;
	private FloatWA position = new FloatWA(3);
	private BoxVRep box;
	
	
	public LandingAreaVRep(remoteApi vrep, int clientID, String name) {
		super();
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		
		/*
		 * Retrieve handle
		 */
		areaH = new IntW(0);
        int r = vrep.simxGetObjectHandle(clientID, name, areaH, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+name+", error : "+r);
        }
        
        /*
         * Read position
         */
		r = vrep.simxGetObjectPosition(clientID, areaH.getValue(), -1, position, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving area position "+getName()+", error : "+r);
        }
	}
	

	
	public String getName() {
		return name;
	}
	
	public FloatWA getPosition() {
		return position;
	}
	
	public IntW getHandle() {
		return areaH;
	}
	
	public BoxVRep getBox() {
		return box;
	}
	
	public void setBox(BoxVRep box) {
		this.box = box;
	}

//	@Override
//	public void update() {
//		IntW boxH = new IntW(3);
//		int r = vrep.simxGetObjectChild(clientID, areaH.getValue(), 0, boxH, remoteApi.simx_opmode_blocking);
//        if(r!=vrep.simx_return_ok) {
//        	System.out.println("ERROR Retriving handle of child of "+name+", error : "+r);
//        }
//        if(box==null || box.getHandle().getValue()!=boxH.getValue()) {
//        	this.box = retriveBoxGivenHandle(boxH.getValue());
//        }
//	}
//	
//	private BoxVRep retriveBoxGivenHandle(int handle) {
//		for(BoxVRep b : boxVrepList)
//			if(b.getHandle().getValue()==handle)
//				return b;
//		return null;
//	}
	
}
