package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.List;

import coppelia.remoteApi;
import unitn.aose.warehousesim.adapter.vrep.BoxVRep;
import unitn.aose.warehousesim.adapter.vrep.LandingAreaVRep;
import unitn.aose.warehousesim.adapter.vrep.RailVRep;
import unitn.aose.warehousesim.adapter.vrep.RobotVRep;
import unitn.aose.warehousesim.agent.AgentJava;
import unitn.aose.warehousesim.api.IRobot;

public class Laucher {

	public static void main(String[] args) throws InterruptedException {

		/*
		 * Connection with VRep
		 */
		
		remoteApi vrep = new remoteApi();
        int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
        
        
        
        /*
         * Boxes
         */

        List<BoxVRep> boxVrepList = new ArrayList<BoxVRep>();
        
        for(int i = 0; i<=14; i++)
        	boxVrepList.add(new BoxVRep(vrep, clientID, "Box"+i));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxA1"));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxA2"));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxB1"));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxB2"));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxC1"));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxC2"));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxD1"));
        boxVrepList.add(new BoxVRep(vrep, clientID, "BoxD2"));
		
        
		
		/*
         * Areas
         */

        List<LandingAreaVRep> areaVrepList = new ArrayList<LandingAreaVRep>();
        
        LandingAreaVRep area_a = new LandingAreaVRep(vrep, clientID, "PickAreaA", boxVrepList);
        area_a.setBox(new BoxVRep(vrep, clientID, "Box0"));
        LandingAreaVRep area_b = new LandingAreaVRep(vrep, clientID, "PickAreaB", boxVrepList);
        area_b.setBox(new BoxVRep(vrep, clientID, "Box5"));
        LandingAreaVRep area_c = new LandingAreaVRep(vrep, clientID, "PickAreaC", boxVrepList);
        area_c.setBox(new BoxVRep(vrep, clientID, "Box1"));
        LandingAreaVRep area_d = new LandingAreaVRep(vrep, clientID, "PickAreaD", boxVrepList);
        area_d.setBox(new BoxVRep(vrep, clientID, "Box14"));

        LandingAreaVRep area_ac = new LandingAreaVRep(vrep, clientID, "ShareAreaAC", boxVrepList);
        area_ac.setBox(new BoxVRep(vrep, clientID, "Box2"));
        LandingAreaVRep area_bc = new LandingAreaVRep(vrep, clientID, "ShareAreaBC", boxVrepList);
        area_bc.setBox(new BoxVRep(vrep, clientID, "Box6"));
        LandingAreaVRep area_bd = new LandingAreaVRep(vrep, clientID, "ShareAreaBD", boxVrepList);
        area_bd.setBox(new BoxVRep(vrep, clientID, "Box4"));
        LandingAreaVRep area_ad = new LandingAreaVRep(vrep, clientID, "ShareAreaAD", boxVrepList);
        area_ad.setBox(new BoxVRep(vrep, clientID, "Box13"));

        LandingAreaVRep area_a1 = new LandingAreaVRep(vrep, clientID, "StorageAreaA1", boxVrepList);
        area_a1.setBox(new BoxVRep(vrep, clientID, "Box12"));
        LandingAreaVRep area_c1 = new LandingAreaVRep(vrep, clientID, "StorageAreaC1", boxVrepList);
        area_c1.setBox(new BoxVRep(vrep, clientID, "Box10"));
        LandingAreaVRep area_c2 = new LandingAreaVRep(vrep, clientID, "StorageAreaC2", boxVrepList);
        area_c2.setBox(new BoxVRep(vrep, clientID, "Box9"));
        LandingAreaVRep area_c3 = new LandingAreaVRep(vrep, clientID, "StorageAreaC3", boxVrepList);
        area_c3.setBox(new BoxVRep(vrep, clientID, "Box7"));
        LandingAreaVRep area_d1 = new LandingAreaVRep(vrep, clientID, "StorageAreaD1", boxVrepList);
        area_d1.setBox(new BoxVRep(vrep, clientID, "Box11"));
        LandingAreaVRep area_d2 = new LandingAreaVRep(vrep, clientID, "StorageAreaD2", boxVrepList);
        area_d2.setBox(new BoxVRep(vrep, clientID, "Box8"));
        LandingAreaVRep area_d3 = new LandingAreaVRep(vrep, clientID, "StorageAreaD3", boxVrepList);
        area_d3.setBox(new BoxVRep(vrep, clientID, "Box3"));

        

        /*
         * Rails
         */
        
        RailVRep rail_a = new RailVRep(vrep, clientID, "rail_a", 15);
//        rail_a.addRightArea(0, area_a);
        rail_a.addLeftArea(3, area_ac);
        rail_a.addRightArea(7, area_a1);
        rail_a.addLeftArea(12, area_ad);
        RailVRep rail_b = new RailVRep(vrep, clientID, "rail_b", 15);
        rail_b.addLeftArea(3, area_bc);
        rail_b.addLeftArea(12, area_bd);
        RailVRep rail_c = new RailVRep(vrep, clientID, "rail_c", 15);
        rail_c.addLeftArea(1, area_c3);
        rail_c.addRightArea(3, area_bc);
        rail_c.addRightArea(6, area_c2);
        rail_c.addLeftArea(9, area_c1);
        rail_c.addRightArea(13, area_ac);
        RailVRep rail_d = new RailVRep(vrep, clientID, "rail_d", 15);
        rail_d.addRightArea(3, area_ad);
        rail_d.addLeftArea(6, area_d1);
        rail_d.addRightArea(9, area_d2);
        rail_d.addRightArea(12, area_bd);
        rail_d.addLeftArea(14, area_d3);
        
        
        /*
         * Robots
         */
        
        List<RobotVRep> robotVrepList = new ArrayList<RobotVRep>();
        
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorA1", rail_a, boxVrepList));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorA2", rail_a, boxVrepList));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorB1", rail_b, boxVrepList));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorB2", rail_b, boxVrepList));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC1", rail_c, boxVrepList));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC2", rail_c, boxVrepList));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorD1", rail_d, boxVrepList));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorD2", rail_d, boxVrepList));
		
		
        
		/*
		 * Preload environment data
		 */
    	for(RobotVRep r : robotVrepList) {
    		r.update();
    	}
    	
    	
    	
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
		
        long ms = System.currentTimeMillis();
        while(true) {
        	Thread.sleep(500);
        	System.out.println(System.currentTimeMillis()-ms+" ms");
        	ms = System.currentTimeMillis();
        	
        	for(RobotVRep r : robotVrepList) {
        		r.update();
        	}

        	for(LandingAreaVRep l : areaVrepList) {
        		l.update();
        	}
        }

	}

}
