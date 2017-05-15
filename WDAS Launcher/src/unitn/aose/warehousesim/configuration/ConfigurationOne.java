package unitn.aose.warehousesim.configuration;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Rail;
import unitn.aose.warehousesim.simulator.Warehouse;

public class ConfigurationOne {
	
	private IConfigurator conf;
	
	Area area_a;
	Area area_b;
	Area area_c;
	Area area_d;

	Area area_ac;
	Area area_bc;
	Area area_bd;
	Area area_ad;
	
	Area area_a1;
	Area area_c1;
	Area area_c2;
	Area area_c3;
	Area area_d1;
	Area area_d2;
	Area area_d3;
	
	public ConfigurationOne(IConfigurator conf) {
		
        /*
         * Boxes
         */
        
    	Box b_0 = conf.defineBox("Box0");
    	Box b_1 = conf.defineBox("Box1");
    	Box b_2 = conf.defineBox("Box2");
    	Box b_3 = conf.defineBox("Box3");
    	Box b_4 = conf.defineBox("Box4");
    	Box b_5 = conf.defineBox("Box5");
    	Box b_6 = conf.defineBox("Box6");
    	Box b_7 = conf.defineBox("Box7");
    	Box b_8 = conf.defineBox("Box8");
    	Box b_9 = conf.defineBox("Box9");
    	Box b_10 = conf.defineBox("Box10");
    	Box b_11 = conf.defineBox("Box11");
    	Box b_12 = conf.defineBox("Box12");
    	Box b_13 = conf.defineBox("Box13");
    	Box b_14 = conf.defineBox("Box14");
    	
    	Box b_a1 = conf.defineBox("BoxA1");
    	Box b_a2 = conf.defineBox("BoxA2");
    	Box b_b1 = conf.defineBox("BoxB1");
    	Box b_b2 = conf.defineBox("BoxB2");
    	Box b_c1 = conf.defineBox("BoxC1");
    	Box b_c2 = conf.defineBox("BoxC2");
    	Box b_d1 = conf.defineBox("BoxD1");
    	Box b_d2 = conf.defineBox("BoxD2");
		
        
		
		/*
         * Areas
         */
    	
    	area_a = conf.defineDepositWithdrawArea("PickAreaA");
    	area_b = conf.defineDepositWithdrawArea("PickAreaB");
    	area_c = conf.defineDepositWithdrawArea("PickAreaC");
    	area_d = conf.defineDepositWithdrawArea("PickAreaD");

    	area_ac = conf.defineArea("ShareAreaAC");
    	area_bc = conf.defineArea("ShareAreaBC");
    	area_bd = conf.defineArea("ShareAreaBD");
    	area_ad = conf.defineArea("ShareAreaAD");
    	
    	area_a1 = conf.defineArea("StorageAreaA1");
    	area_c1 = conf.defineArea("StorageAreaC1");
    	area_c2 = conf.defineArea("StorageAreaC2");
    	area_c3 = conf.defineArea("StorageAreaC3");
    	area_d1 = conf.defineArea("StorageAreaD1");
    	area_d2 = conf.defineArea("StorageAreaD2");
    	area_d3 = conf.defineArea("StorageAreaD3");
    	
    	
    	
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
        
    	Rail rail_a = conf.defineRail("rail_a", 15);
    	Rail rail_b = conf.defineRail("rail_b", 15);
    	Rail rail_c = conf.defineRail("rail_c", 15);
    	Rail rail_d = conf.defineRail("rail_d", 15);
    	
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
        rail_b.addRightArea(0, area_b);
        rail_b.addLeftArea(3, area_bc);
        rail_b.addLeftArea(12, area_bd);
        rail_c.addRightArea(0, area_c);
        rail_c.addLeftArea(1, area_c3);
        rail_c.addRightArea(3, area_bc);
        rail_c.addRightArea(6, area_c2);
        rail_c.addLeftArea(9, area_c1);
        rail_c.addRightArea(12, area_ac);
        rail_d.addRightArea(0, area_d);
        rail_d.addRightArea(3, area_ad);
        rail_d.addLeftArea(6, area_d1);
        rail_d.addRightArea(9, area_d2);
        rail_d.addRightArea(12, area_bd);
        rail_d.addLeftArea(14, area_d3);
        
        
        /*
         * Robots
         */
        
        IRobot robot_a1 = conf.defineCart("RobotMotorA1", rail_a);
        IRobot robot_a2 = conf.defineCart("RobotMotorA2", rail_a);
        IRobot robot_b1 = conf.defineCart("RobotMotorB1", rail_b);
        IRobot robot_b2 = conf.defineCart("RobotMotorB2", rail_b);
        IRobot robot_c1 = conf.defineCart("RobotMotorC1", rail_c);
        IRobot robot_c2 = conf.defineCart("RobotMotorC2", rail_c);
        IRobot robot_d1 = conf.defineCart("RobotMotorD1", rail_d);
        IRobot robot_d2 = conf.defineCart("RobotMotorD2", rail_d);
		
	}
//	
//	
//	
//	public AdapterVRep getEnv() {
//		return env;
//	}

	
	
	public void initialize(Warehouse warehouse) {
    	/*
    	 * Initialize boxes position
    	 * 
    	 * POSIZIONE DEI PACCHI
    	 * per ogni landing area faccio
    	 * - set parent
    	 * - teleport 00 3
    	 * - e posiziono i pacchi sulle landing
    	 */
		
		for(BoxRef boxRef : warehouse.getBoxes()) {
			warehouse.deleteBox(boxRef);
		}
		
		//warehouse.createBox("ShareAreaAC");
		//warehouse.createBox(area_ac);
		//warehouse.createBox(area_bd);
	}

}
