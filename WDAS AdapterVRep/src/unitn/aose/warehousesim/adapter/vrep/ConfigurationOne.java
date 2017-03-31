package unitn.aose.warehousesim.adapter.vrep;

import coppelia.FloatWA;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.data.BoxRef;

public class ConfigurationOne {
	
	private EnvironmentVRep env;
	private remoteApi vrep;
	private int clientID;
	
	AreaVRep area_a;
	AreaVRep area_b;
	AreaVRep area_c;
	AreaVRep area_d;

	AreaVRep area_ac;
	AreaVRep area_bc;
	AreaVRep area_bd;
	AreaVRep area_ad;
	
	AreaVRep area_a1;
	AreaVRep area_c1;
	AreaVRep area_c2;
	AreaVRep area_c3;
	AreaVRep area_d1;
	AreaVRep area_d2;
	AreaVRep area_d3;
	
	public ConfigurationOne() {
		
		/*
		 * Connection with VRep
		 */
		
		vrep = new remoteApi();
        clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
        
        
        
        /*
         * EnvVrep
         */
        env = new EnvironmentVRep(vrep, clientID);
        
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
    	
    	area_a = env.defineArea("PickAreaA");
    	area_b = env.defineArea("PickAreaB");
    	area_c = env.defineArea("PickAreaC");
    	area_d = env.defineArea("PickAreaD");

    	area_ac = env.defineArea("ShareAreaAC");
    	area_bc = env.defineArea("ShareAreaBC");
    	area_bd = env.defineArea("ShareAreaBD");
    	area_ad = env.defineArea("ShareAreaAD");
    	
    	area_a1 = env.defineArea("StorageAreaA1");
    	area_c1 = env.defineArea("StorageAreaC1");
    	area_c2 = env.defineArea("StorageAreaC2");
    	area_c3 = env.defineArea("StorageAreaC3");
    	area_d1 = env.defineArea("StorageAreaD1");
    	area_d2 = env.defineArea("StorageAreaD2");
    	area_d3 = env.defineArea("StorageAreaD3");
    	
    	
    	
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
    	RailVRep rail_b = env.defineRail("rail_b", 7.5f, 15);
    	RailVRep rail_c = env.defineRail("rail_c", 7.5f, 15);
    	RailVRep rail_d = env.defineRail("rail_d", 7.5f, 15);
    	
    	rail_a.addCross(2, rail_c, 11, false);
    	rail_a.addCross(13, rail_d, 4, true);
    	rail_b.addCross(2, rail_c, 4, false);
    	rail_b.addCross(13, rail_d, 11, true);
    	rail_c.addCross(11, rail_a, 2, true);
    	rail_c.addCross(4, rail_b, 2, true);
    	rail_d.addCross(4, rail_a, 13, false);
    	rail_d.addCross(11, rail_b, 13, true);
    	
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
		
	}
	
	
	
	public EnvironmentVRep getEnv() {
		return env;
	}

	
	
	public void initialize() {
    	/*
    	 * Initialize boxes position
    	 * 
    	 * POSIZIONE DEI PACCHI
    	 * per ogni landing area faccio
    	 * - set parent
    	 * - teleport 00 3
    	 * - e posiziono i pacchi sulle landing
    	 */
        for(BoxRef br : env.getBoxes()) {
        	BoxVRep b = env.getBox(br);
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
	}

}
