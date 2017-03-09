package unitn.aose.warehousesim.robot;

import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.Box;
import unitn.aose.warehousesim.api.ILoadUnloadFSM;
import unitn.aose.warehousesim.api.IMovementListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.LoadUnloadArea;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.Rail;

public class RobotVRep implements IRobot {
	
	/*
	 * Parameters
	 */
	final float targetVelocity = 1f;
	
	remoteApi vrep;
	IntW jointH;
	int clientID;

	MovementState movementState = MovementState.stop;
	
	
	public RobotVRep(remoteApi vrep, int clientID, IntW joint) {
		this.vrep = vrep;
		this.clientID = clientID;
		this.jointH = joint;
	}
	
//	void setStato(s) {
//		s = s;
//	}
	
	@Override
	public void moveForward() {
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_blocking);
		movementState = MovementState.runningForward;
	}
	
	@Override
	public void moveBackward() {
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), -targetVelocity, remoteApi.simx_opmode_blocking);
		movementState = MovementState.runningBackward;
	}
	
	@Override
	public void stopHere() {
		// TODO to be fixed
		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), 0f, remoteApi.simx_opmode_blocking);

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
	public LoadUnloadArea getStorageAreaOnLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoadUnloadArea getStorageAreaOnRight() {
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
	public unitn.aose.warehousesim.api.Robot getRobotHaed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public unitn.aose.warehousesim.api.Robot getRobotBehind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rail getRail() {
		// TODO Auto-generated method stub
		return null;
	}

}
