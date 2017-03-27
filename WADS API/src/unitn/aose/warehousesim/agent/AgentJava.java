package unitn.aose.warehousesim.agent;

import java.util.List;

import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;

public class AgentJava implements Runnable {
	
	List<IRobot> robotList;
	
	public AgentJava(List<IRobot> robotList) {
		this.robotList = robotList;
	}

	@Override
	public void run() {
		
		for(final IRobot r : robotList) {
			
			final boolean d = (r.getPosition().get()<=3?true:false);
//    		if(r.getPosition().get()<=3)
//    			d = true;
//    		else if(r.getPosition().get()>=12)
//    			d = false;
    		
			r.getAreaOnLeft().registerListener(new IListener<AreaRef>() {
				@Override
				public void notifyChanged(AreaRef value) {
					r.stopHere();
//					r.loadLeft();
					r.getMovement().registerListener(new IListener<MovementState>() {
						@Override
						public void notifyChanged(MovementState value) {
							if(value==MovementState.stop) {
								r.loadLeft();

					    		if(d)
					    			r.moveForward();
					    		else
					    			r.moveBackward();
					    		
								r.getMovement().unregisterListener(this);
								
								r.getAreaOnLeft().registerListener(new IListener<AreaRef>() {
									@Override
									public void notifyChanged(AreaRef value) {
										r.stopHere();
										r.getMovement().registerListener(new IListener<MovementState>() {
											@Override
											public void notifyChanged(MovementState value) {
												r.unloadLeft();
											}
										});
									}
								});
							}
						}
					});
				}
			});
    		if(d)
    			r.moveForward();
    		else
    			r.moveBackward();
		}
		
//        while(true) {
//        	
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
        	
//	    	for(IRobot r : robotList) {
//	    		if(r.getPosition().get()<=3 && r.getMovement().get()!=MovementState.runningForward)
//	    			r.moveForward();
//	    		else if(r.getPosition().get()>=12 && r.getMovement().get()!=MovementState.runningBackward)
//	    			r.moveBackward();
//	    	}
//        }
	}

}
