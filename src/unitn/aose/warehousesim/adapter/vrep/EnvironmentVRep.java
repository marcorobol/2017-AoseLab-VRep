package unitn.aose.warehousesim.adapter.vrep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;
import coppelia.remoteApi;

public class EnvironmentVRep {
	
	private remoteApi vrep;
	private int clientID;
	
    private List<RailVRep> railVrepList = new ArrayList<RailVRep>();
	private List<LandingAreaVRep> areaVrepList = new ArrayList<LandingAreaVRep>();
    private List<BoxVRep> boxVrepList = new ArrayList<BoxVRep>();
	private List<RobotVRep> robotVrepList = new ArrayList<RobotVRep>();
    
	private Map<LandingArea, BoxVRep> boxes = new HashMap<LandingArea, BoxVRep>();
	
	
    public EnvironmentVRep(remoteApi vrep, int clientID) {
		this.vrep = vrep;
		this.clientID = clientID;
    }
    
    
    
	public RailVRep defineRail(String name, float total_lenght, int steps) {
		RailVRep r = new RailVRep(vrep, clientID, name, total_lenght, steps, this);
		railVrepList.add(r);
		return r;
	}
    
	public RobotVRep defineRobot(String name, RailVRep rail) {
		RobotVRep r = new RobotVRep(vrep, clientID, name, rail, this);
		robotVrepList.add(r);
		return r;
	}
    
	public LandingAreaVRep defineArea(String name) {
		LandingAreaVRep a = new LandingAreaVRep(vrep, clientID, name);
		areaVrepList.add(a);
		return a;
	}
    
	public BoxVRep defineBox(String name) {
		BoxVRep b = new BoxVRep(vrep, clientID, name);
		boxVrepList.add(b);
		return b;
	}
	
//	public void associateAreaToRail(LandingAreaVRep area, RailVRep rail, int step) {
//		rail.addLeftArea(i, l);
//	}
    
	public BoxVRep getBox(LandingArea area) {
		return boxes.get(area);
	}
	
	
    public List<RailVRep> getRailsVrep() {
		return railVrepList;
	}
    
	public List<RobotVRep> getRobotVrepList() {
		return robotVrepList;
	}
	
	public List<LandingAreaVRep> getAreasVrep() {
		return areaVrepList;
	}
	
    public List<BoxVRep> getBoxVrepList() {
		return boxVrepList;
	}
	
}
