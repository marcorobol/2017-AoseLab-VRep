package unitn.aose.warehousesim.adapter.vrep;

import java.text.DecimalFormat;

import unitn.aose.warehousesim.IUpdatable;
import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Cart;
import coppelia.FloatW;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;

public class RobotVRep extends Cart implements IRobot, IUpdatable {
	
	/*
	 * VRep
	 */
	private remoteApi vrep;
	private int clientID;
	private EnvironmentVRep environmentVRep;
	
	private IntW jointH;
	private IntW robotH;
	private IntW connectorH;
	private FloatW posJointVRep = new FloatW(3f);
	private FloatWA positionVRep = new FloatWA(3);
	private FloatWA orientation = new FloatWA(3);
	
	private RailVRep rail;
	
	
	public RobotVRep(remoteApi vrep, int clientID, String name, RailVRep rail, EnvironmentVRep environmentVRep) {
		super(name, rail);
		this.vrep = vrep;
		this.clientID = clientID;
		this.environmentVRep = environmentVRep;
		this.rail = environmentVRep.getRail(rail);
		init();
	}
	
	public void init() {
		/*
		 * Retrive handles
		 */
        this.jointH = new IntW(1);
        int r = vrep.simxGetObjectHandle(clientID, getName(), jointH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+getName()+", error : "+r);
        }
		this.robotH = new IntW(3);
		r = vrep.simxGetObjectChild(clientID, jointH.getValue(), 0, robotH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+getName()+", error : "+r);
        }
		this.connectorH = new IntW(3);
		r = vrep.simxGetObjectChild(clientID, robotH.getValue(), 0, connectorH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+getName()+", error : "+r);
        }
        
        /*
         * Retrieve joint orientation
         */
		r = vrep.simxGetObjectOrientation(clientID, jointH.getValue(), -1, orientation, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+getName()+", error : "+r);
        }
        
        update();
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
		loadBox(getAreaOnLeft().get(), getBoxOnLeft(), LoadUnloadState.loadingLeft);
	}
	
	@Override
	public void loadRight() {
		loadBox(getAreaOnRight().get(), getBoxOnRight(), LoadUnloadState.loadingRight);
	}
	
	public void loadBox(AreaRef areaRef, BoxRef boxRef, LoadUnloadState stateToBe) {
		System.out.println("loadBox");
		AreaVRep ar = environmentVRep.getArea(areaRef);
		BoxVRep box = environmentVRep.getBox(boxRef);
		if(getLoadedBox()==null && box!=null && getMovement().get()==MovementState.stop) {
			System.out.println("setting parent");
			/*
			 * Set parent
			 */
			int r = vrep.simxSetObjectParent(clientID, box.getHandle().getValue(),
					connectorH.getValue(), false, remoteApi.simx_opmode_streaming);
	        if(r!=remoteApi.simx_return_ok) {
	        	System.out.println("ERROR Setting parent of box error : "+r);
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
	        	System.out.println("ERROR Moving box error : "+r);
	        }
	        /*
	         * Update data structure
	         */
	        ar.setBox(null);
	        setLoadedBox(box);
	        getLoadUnload().set(LoadUnloadState.loaded);
		}
	}
	
	@Override
	public void unloadLeft() {
		unloadBox(getAreaOnLeft().get(), LoadUnloadState.unloadingLeft);
	}
	
	@Override
	public void unloadRight() {
		unloadBox(getAreaOnRight().get(), LoadUnloadState.unloadingLeft);
	}
	
	public void unloadBox(AreaRef areaRef, LoadUnloadState stateToBe) {
		AreaVRep area = environmentVRep.getArea(areaRef);
		BoxVRep loadedBox = environmentVRep.getBox(getLoadedBox());
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
	        	System.out.println("ERROR Moving box "+getName()+", error : "+r);
	        }
	        /*
	         * Update data structure
	         */
	        area.setBox(loadedBox);
	        setLoadedBox(null);
	        getLoadUnload().set(LoadUnloadState.unloaded);
		}
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
			Area leftAreaCurrent = getRail().getLeftAreas().get(getPosition().get());
			if(getAreaOnLeft().get()!=leftAreaCurrent) {
				getAreaOnLeft().set(leftAreaCurrent);
			}
			
			/*
			 * Check if there is a different area on right side
			 */
			Area rightAreaCurrent = getRail().getRightAreas().get(getPosition().get());
			if(getAreaOnRight().get()!=rightAreaCurrent) {
				getAreaOnRight().set(rightAreaCurrent);
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
        
        
        
//        DecimalFormat i = new DecimalFormat("00");
//        DecimalFormat f = new DecimalFormat("##.00");
//    	System.out.println("DEBUG Robot "+getName()+
//    			" pos: "+i.format(getPosition().get())+
//    			", vel: "+f.format(getVelocity())+
//    			", force: "+f.format(force.getValue())+
//    			", state: "+getMovement().get()+
//    			", left: "+getAreaOnLeft().get()
//    			);
    	
    	
    	
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
	}

	@Override
	public IObservable<AreaState> getAreaState(AreaRef area) {
		Area a = environmentVRep.getArea(area);
		if(a==area)
			return a.getState();
		return null;
	}
	
}
