package unitn.aose.warehousesim.robot;

import coppelia.FloatW;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.IAgent;
import unitn.aose.warehousesim.api.ILoadUnloadFSM;
import unitn.aose.warehousesim.api.IMovementFSM;
import unitn.aose.warehousesim.api.IMovementListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.Box;
import unitn.aose.warehousesim.api.data.LandingArea;
import unitn.aose.warehousesim.api.data.Rail;
import unitn.aose.warehousesim.api.data.Robot;

public class RobotVRep extends Robot implements IRobot {
	
	/*
	 * Parameters
	 */
	final float targetVelocity = 0.5f;
	final float lenght = 7.5f;
	
	private remoteApi vrep;
	private String name;
	private int clientID;

	private IntW jointH;
	private IntW robotH;
	

	MovementFSM movementFSM = new MovementFSM();
	
	
	public RobotVRep(remoteApi vrep, int clientID, String name, Rail rail) {
		super(rail);
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
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
        updatePosition();
        updateVelocity();
	}
	
//	void setStato(s) {
//		s = s;
//	}
	
	@Override
	public void moveForward() {
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_streaming);
		movementFSM.setState(MovementState.runningForward);
	}
	
	@Override
	public void moveBackward() {
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), -targetVelocity, remoteApi.simx_opmode_streaming);
		movementFSM.setState(MovementState.runningBackward);
	}
	
	@Override
	public void stopHere() {
		Integer index = getPosition();
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), lenght/getRail().getLenght()*index, remoteApi.simx_opmode_streaming);
		movementFSM.setState(MovementState.stopping);
	}
	
	@Override
	public IMovementFSM getMovementFSM() {
		return this.movementFSM;
	}
	
	@Override
	public void load(Box b) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Box unload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILoadUnloadFSM getLoadUnloadFSM() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LandingArea getStorageAreaOnLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LandingArea getStorageAreaOnRight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Box getBoxOnLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Box getBoxOnRight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Box getLoadedBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public unitn.aose.warehousesim.api.data.Robot getRobotHaed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public unitn.aose.warehousesim.api.data.Robot getRobotBehind() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updatePosition() {
		FloatW posVRep = new FloatW(3f);
		int r = vrep.simxGetJointPosition(clientID, jointH.getValue(), posVRep, remoteApi.simx_opmode_streaming);
        if(r!=vrep.simx_return_ok && r!=vrep.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint position "+name+", error : "+r);
        }
		Integer posCurrent = Math.round( posVRep.getValue() / (lenght/getRail().getLenght()) );
		if(this.position!=posCurrent) {
			this.position = posCurrent;
			
    		LandingArea a = getRail().getAreas().get(this.position);
    		if(a!=null) {
    			notifyApproachingToArea(a);
    		}
		}
	}
	
	public void updateVelocity() {
		
		FloatWA velocity = new FloatWA(3);
		FloatWA angular = new FloatWA(3);
		int r = vrep.simxGetObjectVelocity(clientID, robotH.getValue(), velocity, angular, remoteApi.simx_opmode_streaming);
        if(r!=vrep.simx_return_ok && r!=vrep.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint velocity "+name+", error : "+r);
        }
		this.velocity = velocity.getArray()[0] + velocity.getArray()[1] + velocity.getArray()[2];
	}

	public String getName() {
		return name;
	}

	public void setMovementStopState() {
		movementFSM.setState(MovementState.stop);
	}

}
