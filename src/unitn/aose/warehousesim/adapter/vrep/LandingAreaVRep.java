package unitn.aose.warehousesim.adapter.vrep;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;

import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.IUpdatable;
import unitn.aose.warehousesim.api.data.LandingArea;

public class LandingAreaVRep extends LandingArea implements IUpdatable {
	
	private remoteApi vrep;
	private int clientID;
	private String name;
	private List<BoxVRep> boxVrepList;
	
	private IntW areaH;
	private BoxVRep box;

	public LandingAreaVRep(remoteApi vrep, int clientID, String name, List<BoxVRep> boxVrepList) {
		super();
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		this.boxVrepList = boxVrepList;
		
		/*
		 * Retrive handle
		 */
		areaH = new IntW(0);
        int r = vrep.simxGetObjectHandle(clientID, name, areaH, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+name+", error : "+r);
        }
	}

	public LandingAreaVRep(remoteApi vrep, int clientID, String name, String boxName) {
		super();
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		
        box = new BoxVRep(vrep, clientID, boxName);
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

	@Override
	public void update() {
		IntW boxH = new IntW(3);
		int r = vrep.simxGetObjectChild(clientID, areaH.getValue(), 0, boxH, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+name+", error : "+r);
        }
        if(box==null || box.getHandle().getValue()!=boxH.getValue()) {
        	this.box = retriveBoxGivenHandle(boxH.getValue());
        }
	}
	
	private BoxVRep retriveBoxGivenHandle(int handle) {
		for(BoxVRep b : boxVrepList)
			if(b.getHandle().getValue()==handle)
				return b;
		return null;
	}
	
}
