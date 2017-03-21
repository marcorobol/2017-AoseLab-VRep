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
        
        
        /*
         * Rails
         */
        Rail a = new Rail(16);
        Rail b = new Rail(16);
        Rail c = new Rail(16);
        Rail d = new Rail(16);
        
        List<Rail> railList = new ArrayList<Rail>();
        railList.add(a);
        railList.add(b);
        railList.add(c);
        railList.add(d);
        
        List<RobotVRep> robotVrepList = new ArrayList<RobotVRep>();
        
        /*
         * Robots
         */
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorA1", a));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorA2", a));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorB1", b));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorB2", b));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC1", c));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorC2", c));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorD1", d));
		robotVrepList.add(new RobotVRep(vrep, clientID, "RobotMotorD2", d));
        
		
		
		
		
		//List<> listaPacchi;
        
		
        
		/*
		 * LoadUnloadArea
		 List<IntW> LandingAreasHandles = new ArrayList<>();
        
        //Aggiungo le 4 Aree verdi di sharing
        IntW ShareAreaAC = new IntW(1);
        IntW ShareAreaAD = new IntW(1);
        IntW ShareAreaBC = new IntW(1);
        IntW ShareAreaBD = new IntW(1);
        vrep.simxGetObjectHandle(clientID, "ShareAreaAC", ShareAreaAC, remoteApi.simx_opmode_blocking);
        vrep.simxGetObjectHandle(clientID, "ShareAreaAD", ShareAreaAD, remoteApi.simx_opmode_blocking);
        vrep.simxGetObjectHandle(clientID, "ShareAreaBC", ShareAreaBC, remoteApi.simx_opmode_blocking);
        vrep.simxGetObjectHandle(clientID, "ShareAreaBD", ShareAreaBD, remoteApi.simx_opmode_blocking);
        LandingAreasHandles.add(ShareAreaAC);
        LandingAreasHandles.add(ShareAreaAD);
        LandingAreasHandles.add(ShareAreaBC);
        LandingAreasHandles.add(ShareAreaBD);
        
        List<LandingArea> LandingAreasList = new ArrayList<LandingArea>();
        /*
         * Read area positions
        for(IntW h : LandingAreasHandles) {
        	FloatWA pos = new FloatWA(3);
        	vrep.simxGetObjectPosition(clientID, h.getValue(), -1, pos, remoteApi.simx_opmode_blocking);
        	int landingIndex = Math.abs(Math.round(pos.getArray()[0]));
        	System.out.println(landingIndex);
        	LandingAreasList.add(new LandingArea(landingIndex));        	
        }

        a.addLandingArea(0, LandingAreasList.get(0));
        b.addLandingArea(0, LandingAreasList.get(1));
        c.addLandingArea(0, LandingAreasList.get(2));
        d.addLandingArea(0, LandingAreasList.get(3));
        
        System.out.println("PROVA STAMPA AREA INDEX " + LandingAreasList.get(0).getLandinIndex());
        */
        
        
        
        
        // Prova astrazione mappa statica delle LandingAreas
        
		// 2 aree verdi per rotaia
        int area1 = 3;
        int area2 = 12;        
        
        List<Integer> LandingAreasIndexList = new ArrayList<>();
        LandingAreasIndexList.add(area1);
        LandingAreasIndexList.add(area2);
        
        while(true) {
        	//Thread.sleep(100);
        	
        	for(RobotVRep r : robotVrepList) {
        		Integer index = r.getPosition();        		
            	System.out.println("DEBUG Robot: "+r.getName()+" actual index :"+index);
            	
            	
        		if(r.getRail().getAreas().get(index)!=null) {        			
        			r.setState(MovementState.approaching); //??Non dovremmo anche controllare nell if se si trova entro un range di distanza dal robot per settare lo stato di approaching?
        			System.out.println("Indice Area " + r.getRail().getAreas().get(index).getLandinIndex());
        			
        		}
        		
        		if(index == 0){
        			r.setState(MovementState.runningForward);
        			r.moveForward();        			
        		}
        		if(index == 15){
        			r.setState(MovementState.runningBackward);
        			r.moveBackward();      			
        		}        		
        		
        		for(Integer i : LandingAreasIndexList) {
        			
        			if(index >= i-1 && index <= i+1){
        				r.moveApproaching();
        			}
        			else{
        				r.moveNotApproaching();
        			}
        			
        			if (index == i){        			
            			r.setState(MovementState.stop);
            			//r.stopHere();
            			//r.setPosition(5);
            		}
        		}
        		
        		
        	}
        	
        	
        	
        	
//        	r.setMsfMovement("approaching");
        }

	}

}
