package unitn.aose.warehousesim.adapter.vrep;

import java.text.DecimalFormat;
import java.util.List;

import unitn.aose.warehousesim.IUpdatable;
import unitn.aose.warehousesim.Observable;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;
import unitn.aose.warehousesim.api.data.Robot;
import coppelia.FloatW;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;

public class RobotVRep extends Robot implements IRobot, IUpdatable {
	
	/*
	 * Parameters
	 */
	final float targetVelocity = 0.1f;
	final float lenght = 7.5f;
	
	private remoteApi vrep;
	private int clientID;
	private List<BoxVRep> boxVrepList;

	private IntW jointH;
	private IntW robotH;
	private IntW connectorH;
	
	private Observable<MovementState> movementFSM;
	private Observable<LoadUnloadState> loadUnloadFSM;
	private Observable<LandingArea> areaOnLeft;
	private Observable<LandingArea> areaOnRight;
	private Observable<Integer> position = new Observable<Integer>();
	
	private BoxVRep loadedBox;
	private FloatW posJointVRep = new FloatW(3f);
	private Float velocity = 0f;
	
	
	public RobotVRep(remoteApi vrep, int clientID, String name, Rail rail, List<BoxVRep> boxVrepList) {
		super(name, rail);
		this.vrep = vrep;
		this.clientID = clientID;
		this.boxVrepList = boxVrepList;
		
		this.movementFSM = new Observable<MovementState>(MovementState.stop);
		this.loadUnloadFSM = new Observable<LoadUnloadState>();
		this.areaOnLeft = new Observable<LandingArea>();
		this.areaOnRight = new Observable<LandingArea>();
		
		/*
		 * Retrive handle
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
        updatePosition();
        updateVelocity();
	}
	
	public Observable<Integer> getPosition() {
		return position;
	}
	
	public Float getVelocity() {
		return velocity;
	}
	
	@Override
	public void moveForward() {
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), lenght, remoteApi.simx_opmode_streaming);
		movementFSM.set(MovementState.runningForward);
	}
	
	@Override
	public void moveBackward() {
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), -targetVelocity, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), 0, remoteApi.simx_opmode_streaming);
		movementFSM.set(MovementState.runningBackward);
	}
	
	@Override
	public void stopHere() {
		Integer index = getPosition().get();
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), 0, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), lenght/getRail().getLenght()*index, remoteApi.simx_opmode_streaming);
		System.out.println("DEBUG: stopHere() current: "+posJointVRep.getValue()+", target: "+lenght/getRail().getLenght()*index);
		movementFSM.set(MovementState.stopping);
	}
	
	@Override
	public Observable<MovementState> getMovement() {
		return this.movementFSM;
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
		if(loadedBox==null && box!=null && movementFSM.get()==MovementState.stop) {
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
	        loadUnloadFSM.set(stateToBe);
		}
	}
	
	@Override
	public void unloadLeft() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void unloadRight() {
		// TODO Auto-generated method stub
	}
	
	public void unloadBox(LandingAreaVRep area, LoadUnloadState stateToBe) {
		if(loadedBox!=null && area!=null && movementFSM.get()==MovementState.stop) {
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
	        area.setBox(loadedBox);
	        loadedBox = null;
	        loadUnloadFSM.set(stateToBe);
		}
	}
	
	@Override
	public Observable<LoadUnloadState> getLoadUnload() {
		return loadUnloadFSM;
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
	public BoxVRep getLoadedBox() {
		return loadedBox;
	}
	
	@Override
	public Robot getRobotHaed() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Robot getRobotBehind() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void update() {
		updatePosition();
		updateVelocity();
		
		/*
		 * Read joint force
		 */
		FloatW force = new FloatW(0f);
		int r = vrep.simxGetJointForce(clientID, jointH.getValue(), force, remoteApi.simx_opmode_streaming);
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
			movementFSM.set(MovementState.stop);
		}
		
		/*
		 * Update box on it
		 */
		IntW boxH = new IntW(3);
		r = vrep.simxGetObjectChild(clientID, connectorH.getValue(), 0, boxH, remoteApi.simx_opmode_blocking);
        if(r!=vrep.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+getName()+", error : "+r);
        }
        if(loadedBox!=null || loadedBox.getHandle().getValue()!=boxH.getValue()) {
        	this.loadedBox = retriveBoxGivenHandle(boxH.getValue());
        }
	}
	
	private BoxVRep retriveBoxGivenHandle(int handle) {
		for(BoxVRep b : boxVrepList)
			if(b.getHandle().getValue()==handle)
				return b;
		return null;
	}
	
	private void updatePosition() {
		/*
		 * Read joint position
		 */
		int r = vrep.simxGetJointPosition(clientID, jointH.getValue(), posJointVRep, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint position "+getName()+", error : "+r);
        }
        
        /*
         * Update position
         */
		Integer posCurrent = Math.round( posJointVRep.getValue() / (lenght/getRail().getLenght()) );
		if(getPosition().get()!=posCurrent) {
			getPosition().set(posCurrent);
			
			/*
			 * Check if there is a different area on left side
			 */
			LandingArea leftAreaCurrent = getRail().getLeftAreas().get(getPosition().get());
			if(getAreaOnLeft().get()!=leftAreaCurrent) {
				getAreaOnLeft().set(leftAreaCurrent);
			}
			
			/*
			 * Check if there is a different area on right side
			 */
			LandingArea rightAreaCurrent = getRail().getRightAreas().get(getPosition().get());
			if(getAreaOnRight().get()!=rightAreaCurrent) {
				getAreaOnRight().set(rightAreaCurrent);
			}
		}
	}
	
	private void updateVelocity() {
		/*
		 * Read joint first child velocity
		 */
		FloatWA velocity = new FloatWA(3);
		FloatWA angular = new FloatWA(3);
		int r = vrep.simxGetObjectVelocity(clientID, robotH.getValue(), velocity, angular, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint velocity "+getName()+", error : "+r);
        }
		this.velocity = velocity.getArray()[0] + velocity.getArray()[1] + velocity.getArray()[2];
	}
	
	@Override
	public Observable<LandingArea> getAreaOnLeft() {
		return areaOnLeft;
	}
	
	@Override
	public Observable<LandingArea> getAreaOnRight() {
		return areaOnRight;
	}
	
}
