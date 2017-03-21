package unitn.aose.warehousesim.agent;

import java.util.List;

import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.LandingArea;

public class AgentJava implements Runnable {
	
	List<IRobot> robotList;
	
	public AgentJava(List<IRobot> robotList) {
		this.robotList = robotList;
	}

	@Override
	public void run() {
		
		for(final IRobot r : robotList) {
			r.getAreaOnLeft().registerListener(new IListener<LandingArea>() {
				@Override
				public void notifyChanged(LandingArea value) {
					r.stopHere();
//					r.loadLeft();
					r.getMovement().registerListener(new IListener<MovementState>() {
						@Override
						public void notifyChanged(MovementState value) {
							if(value==MovementState.stop) {
								r.loadLeft();
								r.getMovement().unregisterListener(this);
							}
						}
					});
				}
			});
		}
		

    	for(IRobot r : robotList) {
    		if(r.getPosition().get()<=3)
    			r.moveForward();
    		else if(r.getPosition().get()>=12)
    			r.moveBackward();
    	}
		
        while(true) {
        	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	
//	    	for(IRobot r : robotList) {
//	    		if(r.getPosition().get()<=3 && r.getMovement().get()!=MovementState.runningForward)
//	    			r.moveForward();
//	    		else if(r.getPosition().get()>=12 && r.getMovement().get()!=MovementState.runningBackward)
//	    			r.moveBackward();
//	    	}
        }
	}

}
