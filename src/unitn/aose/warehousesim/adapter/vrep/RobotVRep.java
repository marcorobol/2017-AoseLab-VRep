package unitn.aose.warehousesim.adapter.vrep;

import java.text.DecimalFormat;

import unitn.aose.warehousesim.Observable;
import unitn.aose.warehousesim.Robot;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;
import unitn.aose.warehousesim.api.data.RobotRef;
import coppelia.FloatW;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;

public class RobotVRep extends Robot implements IRobot {
	
	/*
	 * Parameters
	 */
	final float targetVelocity = 0.1f;
//	final float lenght = 7.5f;
//	final float step = 0.5f;
	
	/*
	 * VRep
	 */
	private remoteApi vrep;
	private int clientID;
	private EnvironmentVRep environmentVRep;
	private RailVRep rail;

	private Observable<LandingAreaVRep> areaOnLeft;
	private Observable<LandingAreaVRep> areaOnRight;
	
	private IntW jointH;
	private IntW robotH;
	private IntW connectorH;
	private FloatW posJointVRep = new FloatW(3f);
	private FloatWA positionVRep = new FloatWA(3);
	private FloatWA orientation = new FloatWA(3);
	
	
	private BoxVRep loadedBox;
	
	
	public RobotVRep(remoteApi vrep, int clientID, String name, RailVRep rail, EnvironmentVRep environmentVRep) {
		super(name);
		this.vrep = vrep;
		this.clientID = clientID;
		this.rail = rail;
		this.environmentVRep = environmentVRep;
		this.rail = rail;

		this.areaOnLeft = new Observable<LandingAreaVRep>();
		this.areaOnRight = new Observable<LandingAreaVRep>();
		
		/*
		 * Retrive handles
		 */
        this.jointH = new IntW(1);
        int r = vrep.simxGetObjectHandle(clientID, name, jointH, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+name+", error : "+r);
        }
		this.robotH = new IntW(3);
		r = vrep.simxGetObjectChild(clientID, jointH.getValue(), 0, robotH, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+name+", error : "+r);
        }
		this.connectorH = new IntW(3);
		r = vrep.simxGetObjectChild(clientID, robotH.getValue(), 0, connectorH, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+name+", error : "+r);
        }
        
        /*
         * Retrieve joint orientation
         */
		r = vrep.simxGetObjectOrientation(clientID, jointH.getValue(), -1, orientation, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+name+", error : "+r);
        }
        
        update();
	}

	public RailVRep getRail() {
		return rail;
	}

	@Override
	public BoxVRep getBoxOnLeft() {
		if(areaOnLeft.get()!=null)
			return ((LandingAreaVRep)areaOnLeft.get()).getBox();
		else
			return null;
	}
	
	@Override
	public BoxVRep getBoxOnRight() {
		if(areaOnRight.get()!=null)
			return ((LandingAreaVRep)areaOnRight.get()).getBox();
		else
			return null;
	}
	
	@Override
	public IObservable<LandingArea> getAreaOnLeft() {
		return new IObservable<LandingArea>() {
			@Override
			public LandingArea get() {
				return areaOnLeft.get();
			}
			@Override
			public void registerListener(IListener<LandingArea> listener) {
				areaOnLeft.registerListener(new IListener<LandingAreaVRep>() {
					@Override
					public void notifyChanged(LandingAreaVRep value) {
						listener.notifyChanged(value);
					}
				});
			}
			@Override
			public void unregisterListener(IListener<LandingArea> listener) {
				//TODO
			}
		};
	}
	
	@Override
	public IObservable<LandingArea> getAreaOnRight() {
		return new IObservable<LandingArea>() {
			@Override
			public LandingArea get() {
				return areaOnRight.get();
			}
			@Override
			public void registerListener(IListener<LandingArea> listener) {
				areaOnRight.registerListener(new IListener<LandingAreaVRep>() {
					@Override
					public void notifyChanged(LandingAreaVRep value) {
						listener.notifyChanged(value);
					}
				});
			}
			@Override
			public void unregisterListener(IListener<LandingArea> listener) {
				//TODO
			}
		};
	}
	
	@Override
	public void moveForward() {
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), rail.getLenght(), remoteApi.simx_opmode_streaming);
		getMovement().set(MovementState.runningForward);
	}
	
	@Override
	public void moveBackward() {
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), -targetVelocity, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), 0, remoteApi.simx_opmode_streaming);
		getMovement().set(MovementState.runningBackward);
	}
	
	@Override
	public void stopHere() {
		Integer index = getPosition().get();
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), 0, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), rail.getStep()*index, remoteApi.simx_opmode_streaming);
		System.out.println("DEBUG: stopHere() current: "+posJointVRep.getValue()+", target: "+rail.getStep()*index);
		getMovement().set(MovementState.stopping);
	}
	
	@Override
	public void loadLeft() {
		loadBox((LandingAreaVRep)getAreaOnLeft().get(), getBoxOnLeft(), LoadUnloadState.loadingLeft);
	}
	
	@Override
	public void loadRight() {
		loadBox((LandingAreaVRep)getAreaOnRight().get(), getBoxOnRight(), LoadUnloadState.loadingRight);
	}
	
	public void loadBox(LandingAreaVRep area, BoxVRep box, LoadUnloadState stateToBe) {
		if(loadedBox==null && box!=null && getMovement().get()==MovementState.stop) {
			/*
			 * Set parent
			 */
			int r = vrep.simxSetObjectParent(clientID, box.getHandle().getValue(),
					connectorH.getValue(), false, remoteApi.simx_opmode_streaming);
	        if(r!=remoteApi.simx_return_ok) {
	        	System.out.println("ERROR Setting parent of "+getName()+", error : "+r);
	        }
	        /*
	         * Move
	         */
			FloatWA posBoxDest = new FloatWA(3);
			posBoxDest.getArray()[0] = 0f;
			posBoxDest.getArray()[1] = 0f;
			posBoxDest.getArray()[2] = 0.3f;
			r = vrep.simxSetObjectPosition(clientID, box.getHandle().getValue(),
					remoteApi.sim_handle_parent, posBoxDest, remoteApi.simx_opmode_streaming);
	        if(r!=remoteApi.simx_return_ok) {
	        	System.out.println("ERROR Setting parent of "+getName()+", error : "+r);
	        }
	        /*
	         * Update data structure
	         */
	        area.setBox(null);
	        loadedBox = box;
	        getLoadUnload().set(stateToBe);
		}
	}
	
	@Override
	public void unloadLeft() {
		unloadBox(areaOnLeft.get(), LoadUnloadState.unloadingLeft);
	}
	
	@Override
	public void unloadRight() {
		unloadBox(areaOnRight.get(), LoadUnloadState.unloadingLeft);
	}
	
	public void unloadBox(LandingAreaVRep area, LoadUnloadState stateToBe) {
		if(loadedBox!=null && area!=null && getMovement().get()==MovementState.stop) {
			/*
			 * Set parent
			 */
			int r = vrep.simxSetObjectParent(clientID, loadedBox.getHandle().getValue(),
					area.getHandle().getValue(), false, remoteApi.simx_opmode_streaming);
	        if(r!=remoteApi.simx_return_ok) {
	        	System.out.println("ERROR Setting parent of "+getName()+", error : "+r);
	        }
	        /*
	         * Move
	         */
			FloatWA posBoxDest = new FloatWA(3);
			posBoxDest.getArray()[0] = 0f;
			posBoxDest.getArray()[1] = 0f;
			posBoxDest.getArray()[2] = 0.3f;
			r = vrep.simxSetObjectPosition(clientID, loadedBox.getHandle().getValue(),
					remoteApi.sim_handle_parent, posBoxDest, remoteApi.simx_opmode_streaming);
	        if(r!=remoteApi.simx_return_ok) {
	        	System.out.println("ERROR Setting parent of "+getName()+", error : "+r);
	        }
	        /*
	         * Update data structure
	         */
//	        area.setBox(loadedBox);
	        loadedBox = null;
	        getLoadUnload().set(stateToBe);
		}
	}
	
	@Override
	public BoxVRep getLoadedBox() {
		return loadedBox;
	}
	
	@Override
	public RobotRef getRobotHaed() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public RobotRef getRobotBehind() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update() {
		/*
		 * Read position
		 */
		int r = vrep.simxGetObjectPosition(clientID, robotH.getValue(), -1, positionVRep, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint position "+getName()+", error : "+r);
        }
		
		/*
		 * Read joint position
		 */
		r = vrep.simxGetJointPosition(clientID, jointH.getValue(), posJointVRep, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint position "+getName()+", error : "+r);
        }
		Integer posCurrent = Math.round( posJointVRep.getValue() / rail.getStep() );
        
        /*
         * Position changed
         */
		if(getPosition().get()!=posCurrent) {
			getPosition().set(posCurrent);
			
			/*
			 * Check if there is a different area on left side
			 */
//			for(LandingAreaVRep a : environmentVRep.getAreaVrepList()) {
//				if(a.getPosition().orthogonalDistance(positionVRep)<1f) {
//					a.getPosition()[0]-
//				}
//			}
			LandingAreaVRep leftAreaCurrent = rail.getLeftAreas().get(getPosition().get());
			if(areaOnLeft.get()!=leftAreaCurrent) {
				areaOnLeft.set(leftAreaCurrent);
			}
			
			/*
			 * Check if there is a different area on right side
			 */
			LandingAreaVRep rightAreaCurrent = getRail().getRightAreas().get(getPosition().get());
			if(getAreaOnRight().get()!=rightAreaCurrent) {
				areaOnRight.set(rightAreaCurrent);
			}
		}
		
		
		
		/*
		 * Read joint first child velocity
		 */
		FloatWA velocity = new FloatWA(3);
		FloatWA angular = new FloatWA(3);
		r = vrep.simxGetObjectVelocity(clientID, robotH.getValue(), velocity, angular, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint velocity "+getName()+", error : "+r);
        }
		setVelocity(velocity.getArray()[0] + velocity.getArray()[1] + velocity.getArray()[2]);
		
		
		
		/*
		 * Read joint force
		 */
		FloatW force = new FloatW(0f);
		r = vrep.simxGetJointForce(clientID, jointH.getValue(), force, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint position "+getName()+", error : "+r);
        }
        
        
        
        DecimalFormat i = new DecimalFormat("00");
        DecimalFormat f = new DecimalFormat("##.00");
    	System.out.println("DEBUG Robot "+getName()+
    			" pos: "+i.format(getPosition().get())+
    			", vel: "+f.format(getVelocity())+
    			", force: "+f.format(force.getValue())+
    			", state: "+getMovement().get()+
    			", left: "+getAreaOnLeft().get()
    			);
    	
    	
    	
    	/*
    	 * If impossible to move further -> stop
    	 */
		if(force.getValue()>=40f && getVelocity()<0.1f && getVelocity()>-0.1f) {
			if(getMovement().get()!=MovementState.stopping)
				stopHere();
		}
		
		if(getMovement().get()==MovementState.stopping && getVelocity()<0.001f && getVelocity()>-0.001f) {
			vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), 0f, remoteApi.simx_opmode_streaming);
			getMovement().set(MovementState.stop);
		}
		
//		/*
//		 * Update box on it
//		 */
//		IntW boxH = new IntW(3);
//		r = vrep.simxGetObjectChild(clientID, connectorH.getValue(), 0, boxH, remoteApi.simx_opmode_blocking);
//        if(r!=vrep.simx_return_ok) {
//        	System.out.println("ERROR Retriving handle of child of "+getName()+", error : "+r);
//        }
//        if(loadedBox!=null || loadedBox.getHandle().getValue()!=boxH.getValue()) {
//        	this.loadedBox = retriveBoxGivenHandle(boxH.getValue());
//        }
	}
	
//	private BoxVRep retriveBoxGivenHandle(int handle) {
//		for(BoxVRep b : boxVrepList)
//			if(b.getHandle().getValue()==handle)
//				return b;
//		return null;
//	}
	
}
