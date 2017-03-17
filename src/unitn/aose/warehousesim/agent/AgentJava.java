package unitn.aose.warehousesim.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.MovementState;

public class AgentJava implements Runnable {
	
	List<IRobot> robotList;
	
	public AgentJava(List<IRobot> robotList) {
		this.robotList = robotList;
	}

	@Override
	public void run() {

        while(true) {
        	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	
	    	for(IRobot r : robotList) {
	    		if(r.getPosition()<=3 && r.getMovementFSM().getState()!=MovementState.runningForward)
	    			r.moveForward();
	    		else if(r.getPosition()>=12 && r.getMovementFSM().getState()!=MovementState.runningBackward)
	    			r.moveBackward();
	    	}
        }
	}

}
