package unitn.aose.warehousesim.laucher;

import coppelia.FloatW;
import coppelia.IntW;
import coppelia.remoteApi;
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
        
        /*
         * Robot 1
         */
        IntW joint1 = new IntW(1);
        vrep.simxGetObjectHandle(clientID, "?????????????????", joint1, remoteApi.simx_opmode_blocking);
		RobotVRep r1 = new RobotVRep(vrep, clientID, joint1);
		
		/*
		 * Robot 2
		 */
        IntW joint2 = new IntW(1);
        vrep.simxGetObjectHandle(clientID, "?????????????????", joint2, remoteApi.simx_opmode_blocking);
		RobotVRep r2 = new RobotVRep(vrep, clientID, joint2);
        
        //System.createTrhead(r);
		
		//List<> listaPacchi;
        
        while(true) {
        	Thread.sleep(500);
        	
        	FloatW posJ1 = new FloatW(0f);
        	vrep.simxGetJointPosition(clientID, joint1.getValue(), posJ1, remoteApi.simx_opmode_blocking);
        	
//        	r.setMsfMovement("approaching");
        }

	}

}
