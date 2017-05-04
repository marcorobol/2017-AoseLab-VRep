package unitn.aose.warehousesim.configuration;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Rail;
import unitn.aose.warehousesim.simulator.Warehouse;

public class ConfigurationThree {

	Area area_a;
	Area area_b;
	Area area_c;
	Area area_d;
	Area area_e;
	Area area_f;
	Area area_ac;
	Area area_bc;
	Area area_bd;
	Area area_ad;
	Area area_ec;
	Area area_ed;
	Area area_a1;
	Area area_c1;
	Area area_c2;
	Area area_c3;
	Area area_d1;
	Area area_d2;
	Area area_d3;
	Area area_e1;
	Area area_fc;
	Area area_fd;
	
	
	
	public ConfigurationThree(IConfigurator conf) {
		
        
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
    	Box b_e1 = conf.defineBox("BoxE1");
    	Box b_e2 = conf.defineBox("BoxE2");
    	Box b_f1 = conf.defineBox("BoxF1");
    	Box b_f2 = conf.defineBox("BoxF2");
		
        
		
		/*
         * Areas
         */
    	
    	area_a = conf.defineDepositWithdrawArea("PickAreaA");
    	area_b = conf.defineDepositWithdrawArea("PickAreaB");
    	area_c = conf.defineDepositWithdrawArea("PickAreaC");
    	area_d = conf.defineDepositWithdrawArea("PickAreaD");
    	area_e = conf.defineDepositWithdrawArea("PickAreaE");
    	area_f = conf.defineDepositWithdrawArea("PickAreaF");

    	area_ac = conf.defineArea("ShareAreaAC");
    	area_bc = conf.defineArea("ShareAreaBC");
    	area_bd = conf.defineArea("ShareAreaBD");
    	area_ad = conf.defineArea("ShareAreaAD");
    	area_ec = conf.defineArea("ShareAreaEC");
    	area_ed = conf.defineArea("ShareAreaED");
    	
    	
    	area_a1 = conf.defineArea("StorageAreaA1");
    	area_c1 = conf.defineArea("StorageAreaC1");
    	area_c2 = conf.defineArea("StorageAreaC2");
    	area_c3 = conf.defineArea("StorageAreaC3");
    	area_d1 = conf.defineArea("StorageAreaD1");
    	area_d2 = conf.defineArea("StorageAreaD2");
    	area_d3 = conf.defineArea("StorageAreaD3");
    	area_e1 = conf.defineArea("StorageAreaE1");
    	area_fc = conf.defineArea("ShareAreaFC");
    	area_fd = conf.defineArea("ShareAreaFD");
    	
    	
    	
    	
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
        
    	Rail rail_a = conf.defineRail("RailA", 15);
    	Rail rail_b = conf.defineRail("RailB", 15);
    	Rail rail_c = conf.defineRail("RailC", 15);
    	Rail rail_d = conf.defineRail("RailD", 15);
    	Rail rail_e = conf.defineRail("RailE", 15);
    	Rail rail_f = conf.defineRail("RailF", 15);
    	
    	// INCROCI 
    	rail_a.addCross(2, rail_c, 11, false);// punto 2 della rotaia A si incrocia all'indice 11 della rotaia c.  false = direzionata verso sinistra
    	rail_a.addCross(13, rail_d, 4, true);
    	
    	rail_b.addCross(2, rail_d, 11, true);
    	rail_b.addCross(13, rail_c, 4, false);	
    	
    	rail_c.addCross(4, rail_b, 13, false);
    	rail_c.addCross(6, rail_e, 2, true);
    	rail_c.addCross(9, rail_f, 2, true);
    	rail_c.addCross(11, rail_a, 2, true);
    	
    	rail_d.addCross(4, rail_a, 13, true);
    	rail_d.addCross(6, rail_f, 13, true);
    	rail_d.addCross(9, rail_e, 13, true);
    	rail_d.addCross(11, rail_b, 2, false);
    	
    	rail_e.addCross(2, rail_c, 6, false);
    	rail_e.addCross(13, rail_d, 9, true);

    	// AREE
        rail_a.addLeftArea(1, area_a);
        rail_a.addRightArea(1, area_c1);
        rail_a.addLeftArea(3, area_ac);
        rail_a.addRightArea(3, area_fc);
        rail_a.addRightArea(7, area_a1);
        rail_a.addLeftArea(12, area_ad);
        rail_a.addRightArea(12, area_fd);
        rail_a.addRightArea(14, area_d1);
        
        rail_b.addLeftArea(3, area_bd);
        rail_b.addRightArea(3, area_d2);
        rail_b.addRightArea(7, area_e1);
        rail_b.addLeftArea(12, area_bc);
        rail_b.addRightArea(12, area_c2);
        rail_b.addLeftArea(14, area_b);
        
        rail_c.addLeftArea(1, area_c3);
        rail_c.addRightArea(3, area_bc);
        rail_c.addLeftArea(3, area_b);
        rail_c.addRightArea(5, area_c2);
        rail_c.addLeftArea(7, area_e);        
        rail_c.addRightArea(7, area_ec);
        rail_c.addLeftArea(8, area_f);
        rail_c.addLeftArea(10, area_c1);
        rail_c.addRightArea(10, area_fc);
        rail_c.addRightArea(12, area_ac);
        rail_c.addLeftArea(12, area_a);
        rail_c.addRightArea(14, area_c);
        
        rail_d.addRightArea(1, area_d);
        rail_d.addRightArea(3, area_ad);
        rail_d.addLeftArea(5, area_d1);
        rail_d.addRightArea(5, area_fd);
        rail_d.addRightArea(8, area_ed);
        rail_d.addRightArea(10, area_d2);
        rail_d.addRightArea(12, area_bd);
        rail_d.addLeftArea(14, area_d3);
        
        rail_e.addLeftArea(1, area_e);
        rail_e.addRightArea(3, area_c2);
        rail_e.addLeftArea(3, area_ec);
        rail_e.addRightArea(7, area_e1);
        rail_e.addRightArea(12, area_d2);
        rail_e.addLeftArea(12, area_ed);
        
        rail_f.addLeftArea(1, area_c1);
        rail_f.addRightArea(1, area_f);
        rail_f.addLeftArea(3, area_fc);
        rail_f.addLeftArea(1, area_c1);
        rail_f.addLeftArea(7, area_a1);
        rail_f.addLeftArea(12, area_fd);
        rail_f.addLeftArea(14, area_d1);
        
        
        
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
        IRobot robot_e1 = conf.defineCart("RobotMotorE1", rail_e);
        IRobot robot_e2 = conf.defineCart("RobotMotorE2", rail_e);
        IRobot robot_f1 = conf.defineCart("RobotMotorF1", rail_f);
        IRobot robot_f2 = conf.defineCart("RobotMotorF2", rail_f);
		
	}
	
	
	
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
        
//        for(DepositWithdrawAreaRef ar : warehouse.getDepositWithdrawAreas()) {
//        	ITellerMachine a = warehouse.getTellerMachine(ar);
//        	a.createBox();
//        	a.requestDeposit();
//        }
	}

}
