package unitn.aose.warehousesim.laucher;

import java.util.ArrayList;
import java.util.List;

import coppelia.FloatW;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;
import unitn.aose.warehousesim.robot.RobotVRep;

public class Laucher {

	public static void main(String[] args) throws InterruptedException {


//      a = new Parametrizzzaion Amb();
//      
//      la1 = a.addLoadUnloadArea(2, 6);
//      
//      r = a.addRail();
//      sp1 = a.addStopPosition(r, 0);
//      sp2 = a.addStopPosition(r, 3);
//      sp3 = a.addStopPosition(r, 5);
//      a.linkStorageArea(sp1, la1, );
//      
//      a.setBoxPosition(b1, la1);
//      
//      r1 = a.addRobot(r, "robot1", "joint1");
//      
//      a.addJ(r1, "joint1", -2.5, 0, 5.0, 0, 0, 5);
		
		
		remoteApi vrep = new remoteApi();
		
        int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);

        
        
        
        
        List<RobotVRep> robotVrepList = new ArrayList<RobotVRep>();
        /*
         * Robots
         */
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC1"));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC2"));
        
		
		
		
		
        //System.createTrhead(r);
		
		//List<> listaPacchi;
        
		
        List<IntW> loadUnloadAresHandles = new ArrayList<>();
		/*
		 * LoadUnloadArea
		 */
        IntW pickAreaA = new IntW(1);
        vrep.simxGetObjectHandle(clientID, "PickAreaA", pickAreaA, remoteApi.simx_opmode_blocking);
        loadUnloadAresHandles.add(pickAreaA);
        
        Rail a = new Rail(16);
        
        List<LandingArea> loadUnloadAreas = new ArrayList<LandingArea>();
        /*
         * Read area positions
         */
        for(IntW h : loadUnloadAresHandles) {
        	FloatWA pos = new FloatWA(3);
        	vrep.simxGetObjectPosition(clientID, h.getValue(), -1, pos, remoteApi.simx_opmode_blocking);
        	loadUnloadAreas.add(new LandingArea());
        }
        
        
        while(true) {
        	Thread.sleep(500);
        	
        	for(RobotVRep r : robotVrepList) {
        		Integer index = r.getPosition();
            	System.out.println("DEBUG Robot"+r.getName()+"actual index :"+index);
        		if(r.getRail().getAreas().get(index)!=null) {
        			r.setState(MovementState.approaching);
        		}
        	}
        	
        	
        	
        	
//        	r.setMsfMovement("approaching");
        }

	}

}
