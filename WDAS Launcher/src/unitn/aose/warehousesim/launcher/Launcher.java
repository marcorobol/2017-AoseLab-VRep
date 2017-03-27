package unitn.aose.warehousesim.launcher;

import java.util.ArrayList;
import java.util.List;

import coppelia.FloatWA;
import coppelia.remoteApi;
import unitn.aose.warehousesim.adapter.vrep.BoxVRep;
import unitn.aose.warehousesim.adapter.vrep.EnvironmentVRep;
import unitn.aose.warehousesim.adapter.vrep.AreaVRep;
import unitn.aose.warehousesim.adapter.vrep.RailVRep;
import unitn.aose.warehousesim.adapter.vrep.RobotVRep;
import unitn.aose.warehousesim.agent.AgentGui;
import unitn.aose.warehousesim.agent.AgentJava;
import unitn.aose.warehousesim.api.IRobot;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * Connection with VRep
		 */
		
		remoteApi vrep = new remoteApi();
        int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
        
        
        
        /*
         * EnvVrep
         */
        EnvironmentVRep env = new EnvironmentVRep(vrep, clientID);
        
        /*
         * Boxes
         */
        
    	BoxVRep b_0 = env.defineBox("Box0");
    	BoxVRep b_1 = env.defineBox("Box1");
    	BoxVRep b_2 = env.defineBox("Box2");
    	BoxVRep b_3 = env.defineBox("Box3");
    	BoxVRep b_4 = env.defineBox("Box4");
    	BoxVRep b_5 = env.defineBox("Box5");
    	BoxVRep b_6 = env.defineBox("Box6");
    	BoxVRep b_7 = env.defineBox("Box7");
    	BoxVRep b_8 = env.defineBox("Box8");
    	BoxVRep b_9 = env.defineBox("Box9");
    	BoxVRep b_10 = env.defineBox("Box10");
    	BoxVRep b_11 = env.defineBox("Box11");
    	BoxVRep b_12 = env.defineBox("Box12");
    	BoxVRep b_13 = env.defineBox("Box13");
    	BoxVRep b_14 = env.defineBox("Box14");
    	
    	BoxVRep b_a1 = env.defineBox("BoxA1");
    	BoxVRep b_a2 = env.defineBox("BoxA2");
    	BoxVRep b_b1 = env.defineBox("BoxB1");
    	BoxVRep b_b2 = env.defineBox("BoxB2");
    	BoxVRep b_c1 = env.defineBox("BoxC1");
    	BoxVRep b_c2 = env.defineBox("BoxC2");
    	BoxVRep b_d1 = env.defineBox("BoxD1");
    	BoxVRep b_d2 = env.defineBox("BoxD2");
		
        
		
		/*
         * Areas
         */
    	
    	AreaVRep area_a = env.defineArea("PickAreaA");
    	AreaVRep area_b = env.defineArea("PickAreaB");
    	AreaVRep area_c = env.defineArea("PickAreaC");
    	AreaVRep area_d = env.defineArea("PickAreaD");

    	AreaVRep area_ac = env.defineArea("ShareAreaAC");
    	AreaVRep area_bc = env.defineArea("ShareAreaBC");
    	AreaVRep area_bd = env.defineArea("ShareAreaBD");
    	AreaVRep area_ad = env.defineArea("ShareAreaAD");
    	
    	AreaVRep area_a1 = env.defineArea("StorageAreaA1");
    	AreaVRep area_c1 = env.defineArea("StorageAreaC1");
    	AreaVRep area_c2 = env.defineArea("StorageAreaC2");
    	AreaVRep area_c3 = env.defineArea("StorageAreaC3");
    	AreaVRep area_d1 = env.defineArea("StorageAreaD1");
    	AreaVRep area_d2 = env.defineArea("StorageAreaD2");
    	AreaVRep area_d3 = env.defineArea("StorageAreaD3");
    	
    	
    	
    	/*
    	 * Initialize boxes position
    	 * 
    	 * POSIZIONE DEI PACCHI
    	 * per ogni landing area faccio
    	 * - set parent
    	 * - teleport 00 3
    	 * - e posiziono i pacchi sulle landing
    	 */
        for(BoxVRep b : env.getBoxVrepList()) {
	        /*
	         * Move
	         */
			FloatWA posBoxStart = new FloatWA(3);
			posBoxStart.getArray()[0] = 100f;
			posBoxStart.getArray()[1] = 100f;
			posBoxStart.getArray()[2] = 100f;
			int r = vrep.simxSetObjectPosition(clientID, b.getHandle().getValue(),
					remoteApi.sim_handle_parent, posBoxStart, remoteApi.simx_opmode_blocking);
	        if(r!=remoteApi.simx_return_ok) {
	        	System.out.println("ERROR Setting initial position of box " + b.getName() +". Error : "+r);
	        }
        }
        env.createBoxIn(area_ac);
        env.createBoxIn(area_bd);
//      area_a.setBox(b_0);
//      area_b.setBox(b_5);
//      area_c.setBox(b_1);
//      area_d.setBox(b_14);
//
//      area_ac.setBox(b_2);
//      area_bc.setBox(b_6);
//      area_bd.setBox(b_4);
//      area_ad.setBox(b_13);
//
//      area_a1.setBox(b_12);
//      area_c1.setBox(b_10);
//      area_c2.setBox(b_9);
//      area_c3.setBox(b_7);
//      area_d1.setBox(b_11);
//      area_d2.setBox(b_8);
//      area_d3.setBox(b_3);
//      
        
        
        
        /*
         * Rails
         */
        
    	RailVRep rail_a = env.defineRail("rail_a", 7.5f, 15);
    	RailVRep rail_b = env.defineRail("rail_a", 7.5f, 15);
    	RailVRep rail_c = env.defineRail("rail_a", 7.5f, 15);
    	RailVRep rail_d = env.defineRail("rail_a", 7.5f, 15);
    	
        rail_a.addRightArea(0, area_a);
        rail_a.addLeftArea(3, area_ac);
        rail_a.addRightArea(7, area_a1);
        rail_a.addLeftArea(12, area_ad);
        rail_b.addLeftArea(3, area_bc);
        rail_b.addLeftArea(12, area_bd);
        rail_c.addLeftArea(1, area_c3);
        rail_c.addRightArea(3, area_bc);
        rail_c.addRightArea(6, area_c2);
        rail_c.addLeftArea(9, area_c1);
        rail_c.addRightArea(12, area_ac);
        rail_d.addRightArea(3, area_ad);
        rail_d.addLeftArea(6, area_d1);
        rail_d.addRightArea(9, area_d2);
        rail_d.addRightArea(12, area_bd);
        rail_d.addLeftArea(14, area_d3);
        
        
        /*
         * Robots
         */
        
        IRobot robot_a1 = env.defineRobot("RobotMotorA1", rail_a);
        IRobot robot_a2 = env.defineRobot("RobotMotorA2", rail_a);
        IRobot robot_b1 = env.defineRobot("RobotMotorB1", rail_b);
        IRobot robot_b2 = env.defineRobot("RobotMotorB2", rail_b);
        IRobot robot_c1 = env.defineRobot("RobotMotorC1", rail_c);
        IRobot robot_c2 = env.defineRobot("RobotMotorC2", rail_c);
        IRobot robot_d1 = env.defineRobot("RobotMotorD1", rail_d);
        IRobot robot_d2 = env.defineRobot("RobotMotorD2", rail_d);
		
		
        
//		/*
//		 * Preload environment data
//		 */
//    	for(RobotVRep r : env.getRobotVrepList()) {
//    		r.update();
//    	}
    	
    	
    	
		/*
		 * Agents
		 */
		List<IRobot> robotList = new ArrayList<IRobot>();
		for(IRobot r : env.getRobotVrepList()) {
			robotList.add(r);
		}
//		robotList.add(robot_a1);
//		robotList.add(robot_d1);
//		robotList.add(robot_d2);
//		new Thread(new AgentJava(robotList)).start();

//		List<IRobot> guiRobotList = new ArrayList<IRobot>();
//		guiRobotList.add(robot_a2);
		new AgentGui(robotList);
        
        
        
        /*
         * Simulation cycle
         */
		
        long ms = System.currentTimeMillis();
        while(true) {
        	Thread.sleep(500);
//        	System.out.println(System.currentTimeMillis()-ms+" ms");
        	ms = System.currentTimeMillis();
        	
        	for(RobotVRep r : env.getRobotVrepList()) {
        		r.update();
        	}
        }

	}

}
