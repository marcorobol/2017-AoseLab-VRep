package unitn.aose.warehousesim.robot;

import coppelia.FloatW;
import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.ILoadUnloadFSM;
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
	float targetVelocity = 1f;
	final float lenght = 7.5f;
	
	private remoteApi vrep;
	private IntW jointH;
	private String name;
	private int clientID;
	

	MovementState movementState = MovementState.stop;
	
	
	public RobotVRep(remoteApi vrep, int clientID, String name, Rail r) {
		super(r);
		this.vrep = vrep;
		this.clientID = clientID;
		this.name = name;
		/*
		 * Retrive handle
		 */
        this.jointH = new IntW(1);
        vrep.simxGetObjectHandle(clientID, name, jointH, remoteApi.simx_opmode_blocking);
	}
	
//	void setStato(s) {
//		s = s;
//	}
	
	@Override
	public void moveForward() {
		targetVelocity = 1f;
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_blocking);
		movementState = MovementState.runningForward;
		
	}
	
	@Override
	public void moveBackward() {
		targetVelocity = -1f;
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_blocking);
		movementState = MovementState.runningBackward;
	}
	
	@Override
	public void moveApproaching() {
		// TODO Auto-generated method stub
		
		/*FloatW force = new FloatW(3f);
		vrep.simxGetJointForce(clientID, jointH.getValue(), force, remoteApi.simx_opmode_blocking);*/
		
		//vrep.simxSetJointForce(clientID, jointH.getValue(), 0, remoteApi.simx_opmode_blocking);
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity/2, remoteApi.simx_opmode_blocking);
		
	}
	
	@Override
	public void moveNotApproaching() {
		// TODO Auto-generated method stub
		//vrep.simxSetJointForce(clientID, jointH.getValue(), 50, remoteApi.simx_opmode_blocking);
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_blocking);
	}
	
	@Override
	public void stopHere() {
		Integer index = getPosition();
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), 0f, remoteApi.simx_opmode_blocking);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), 5f, remoteApi.simx_opmode_blocking);
		
	}

	public MovementState setState(MovementState s) {
		return this.movementState = s;
	}
	
	@Override
	public MovementState getState() {
		return movementState;
	}

	@Override
	public void registerMovementListener(IMovementListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterMovementListener(IMovementListener listener) {
		// TODO Auto-generated method stub
		
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
	
	@Override
	public Integer getPosition() {
		FloatW position = new FloatW(3f);
		vrep.simxGetJointPosition(clientID, jointH.getValue(), position, remoteApi.simx_opmode_blocking);
		return Math.round( position.getValue() / (lenght/getRail().getLenght()) );
	}
	
	
	@Override
	public void setPosition(Integer position) {
		FloatW pos = new FloatW(3f);
		vrep.simxGetJointPosition(clientID, jointH.getValue(), pos, remoteApi.simx_opmode_blocking);
		vrep.simxSetJointPosition(clientID, jointH.getValue(), pos.getValue(), remoteApi.simx_opmode_blocking);
	}

	public String getName() {
		return name;
	}

	public void setVelocity(float velocity){
		 this.targetVelocity = velocity;
	}
	
	

	

}
