package unitn.aose.warehousesim.laucher;

import java.util.ArrayList;
import java.util.List;

import coppelia.remoteApi;
import unitn.aose.warehousesim.agent.AgentJava;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.robot.LandingAreaVRep;
import unitn.aose.warehousesim.robot.RailVRep;
import unitn.aose.warehousesim.robot.RobotVRep;

public class Laucher {

	public static void main(String[] args) throws InterruptedException {

		/*
		 * Connection with VRep
		 */
		
		remoteApi vrep = new remoteApi();
        int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
		
        
		
		/*
         * Areas
         */
        
        LandingAreaVRep area_a = new LandingAreaVRep(vrep, clientID, "PickAreaA");
        LandingAreaVRep area_b = new LandingAreaVRep(vrep, clientID, "PickAreaB");
        LandingAreaVRep area_c = new LandingAreaVRep(vrep, clientID, "PickAreaC");
        LandingAreaVRep area_d = new LandingAreaVRep(vrep, clientID, "PickAreaD");

        LandingAreaVRep area_ac = new LandingAreaVRep(vrep, clientID, "ShareAreaAC");
        LandingAreaVRep area_bc = new LandingAreaVRep(vrep, clientID, "ShareAreaBC");
        LandingAreaVRep area_bd = new LandingAreaVRep(vrep, clientID, "ShareAreaBD");
        LandingAreaVRep area_ad = new LandingAreaVRep(vrep, clientID, "ShareAreaAD");

        LandingAreaVRep area_a1 = new LandingAreaVRep(vrep, clientID, "StorageAreaA1");
        LandingAreaVRep area_c1 = new LandingAreaVRep(vrep, clientID, "StorageAreaC1");
        LandingAreaVRep area_c2 = new LandingAreaVRep(vrep, clientID, "StorageAreaC2");
        LandingAreaVRep area_c3 = new LandingAreaVRep(vrep, clientID, "StorageAreaC3");
        LandingAreaVRep area_d1 = new LandingAreaVRep(vrep, clientID, "StorageAreaD1");
        LandingAreaVRep area_d2 = new LandingAreaVRep(vrep, clientID, "StorageAreaD2");
        LandingAreaVRep area_d3 = new LandingAreaVRep(vrep, clientID, "StorageAreaD3");

        

        /*
         * Rails
         */
        
        RailVRep rail_a = new RailVRep(vrep, clientID, "rail_a", 16);
        rail_a.addLoadUnloadArea(1, area_a);
        rail_a.addLoadUnloadArea(3, area_ac);
        rail_a.addLoadUnloadArea(7, area_a1);
        rail_a.addLoadUnloadArea(12, area_ad);
        RailVRep rail_b = new RailVRep(vrep, clientID, "rail_b", 16);
        RailVRep rail_c = new RailVRep(vrep, clientID, "rail_c", 16);
        RailVRep rail_d = new RailVRep(vrep, clientID, "rail_d", 16);
        
        
        /*
         * Robots
         */
        
        List<RobotVRep> robotVrepList = new ArrayList<RobotVRep>();
        
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorA1", rail_a));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorA2", rail_a));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorB1", rail_b));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorB2", rail_b));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC1", rail_c));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC2", rail_c));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorD1", rail_d));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorD2", rail_d));
		
		
        
		/*
		/*
		 * Agents
		 */
		List<IRobot> robotList = new ArrayList<IRobot>();
		for(IRobot r : robotVrepList) {
			robotList.add(r);
		}
		new Thread(new AgentJava(robotList)).start();
        
        
        
        /*
         * Simulation cycle
         */
        
        while(true) {
        	Thread.sleep(500);
        	
        	for(RobotVRep r : robotVrepList) {
        		r.updatePosition();
        		r.updateVelocity();
            	
        		LandingArea a = r.getRail().getAreas().get(r.getPosition());
        		if(a!=null) {
        			r.getAgent().notifyApproachingToArea(a);
        		}
        		
    			if(r.getMovementFSM().getState()==MovementState.stopping && r.getVelocity()<0.001f && r.getVelocity()>-0.001f)
        			r.setMovementStopState();
        		
            	System.out.println("DEBUG Robot "+r.getName()+" pos: "+r.getPosition()+", vel: "+r.getVelocity()+", state: "+r.getMovementFSM().getState());
        	}
        	
        }

	}

}
