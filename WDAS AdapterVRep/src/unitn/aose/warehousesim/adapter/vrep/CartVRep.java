package unitn.aose.warehousesim.adapter.vrep;

import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.data.Cart;
import unitn.aose.warehousesim.data.Rail;
import unitn.aose.warehousesim.simulator.IAdapterCart;

import java.text.DecimalFormat;

import coppelia.FloatW;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;

public class CartVRep implements IAdapterCart {

	/// used to format numbers appropriately ///
    private static final DecimalFormat intFormat = new DecimalFormat("00");
    private static final DecimalFormat floatFormat = new DecimalFormat("00.00");
	
	/*
	 * VRep
	 */
	private final remoteApi vrep;
	private final int clientID;
	private final Cart cart;
	private final String cartName;
	private final Rail rail;
	private final AdapterVRep adapter;
	
	private IntW jointH;
	private IntW robotH;
	private IntW connectorH;
	private FloatW positionJointVRep = new FloatW(3f);
	private FloatW forceJointVRep = new FloatW(0f);
	private FloatWA velocityVRep = new FloatWA(3);
	private float velocity = 0;
	private FloatWA angularVelocityVRep = new FloatWA(3);
	
	
	
	public CartVRep(remoteApi vrep, int clientID, Cart cart, AdapterVRep adapter) {
		this.vrep = vrep;
		this.clientID = clientID;
		this.cart = cart;
		this.cartName = cart.getName();
		this.rail = cart.getRail();
		this.adapter = adapter;
		init();
	}
	
	public void init() {
		/*
		 * Retrive handles
		 */
        this.jointH = new IntW(1);
        int r = vrep.simxGetObjectHandle(clientID, cartName, jointH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of "+cartName+", error : "+r);
        }
		this.robotH = new IntW(3);
		r = vrep.simxGetObjectChild(clientID, jointH.getValue(), 0, robotH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+cartName+", error : "+r);
        }
		this.connectorH = new IntW(3);
		r = vrep.simxGetObjectChild(clientID, robotH.getValue(), 0, connectorH, remoteApi.simx_opmode_blocking);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Retriving handle of child of "+cartName+", error : "+r);
        }

		/*
		 * Start streaming
		 */
		vrep.simxGetObjectVelocity(clientID, robotH.getValue(), velocityVRep, angularVelocityVRep, remoteApi.simx_opmode_streaming);
		vrep.simxGetJointPosition(clientID, jointH.getValue(), positionJointVRep, remoteApi.simx_opmode_streaming);
		vrep.simxGetJointForce(clientID, jointH.getValue(), forceJointVRep, remoteApi.simx_opmode_streaming);
	}
	
	@Override
	public void moveForward() {
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), targetVelocity, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), adapter.getRail(rail).getStepLenght()*rail.getSteps(), remoteApi.simx_opmode_oneshot);
	}
	
	@Override
	public void moveBackward() {
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), -targetVelocity, remoteApi.simx_opmode_streaming);
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), 0, remoteApi.simx_opmode_oneshot);
	}
	
	@Override
	public void moveTo(Integer index) {
//		vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), 0, remoteApi.simx_opmode_streaming);
		float apos = adapter.getRail(rail).getStepLenght()*index.intValue();
		vrep.simxSetJointTargetPosition(clientID, jointH.getValue(), apos, remoteApi.simx_opmode_oneshot);
		//System.out.println("DEBUG: "+cartName+" stopHere() current: "+positionJointVRep.getValue()+", target: "+apos);
	}
	
	@Override
	public void loadBox(BoxRef boxRef, Boolean rightSideOrLeftSide) {
		System.out.println("loadBox");
		BoxVRep box = adapter.getBox(boxRef);
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
	}
	
	@Override
	public void unloadBoxInArea(BoxRef boxRef, AreaRef areaRef, Boolean rightSideOrLeftSide) {
		BoxVRep loadedBox = adapter.getBox(boxRef);
		AreaVRep area = adapter.getArea(areaRef);
		/*
		 * Set parent
		 */
		int r = vrep.simxSetObjectParent(clientID, loadedBox.getHandle().getValue(),
				area.getHandle().getValue(), false, remoteApi.simx_opmode_oneshot);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Setting parent of "+cartName+", error : "+r);
        }
        /*
         * Move
         */
		FloatWA posBoxDest = new FloatWA(3);
		posBoxDest.getArray()[0] = 0f;
		posBoxDest.getArray()[1] = 0f;
		posBoxDest.getArray()[2] = 0.3f;
		r = vrep.simxSetObjectPosition(clientID, loadedBox.getHandle().getValue(),
				remoteApi.sim_handle_parent, posBoxDest, remoteApi.simx_opmode_oneshot);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Moving box "+cartName+", error : "+r);
        }
	}
	
	public void update() {
		/*
		 * Read joint position
		 */
		int r = vrep.simxGetJointPosition(clientID, jointH.getValue(), positionJointVRep, remoteApi.simx_opmode_buffer);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint position "+cart.getName()+", error : "+r);
        }
		Integer posCurrent = Math.round( positionJointVRep.getValue() / adapter.getRail(rail).getStepLenght() );
		cart.getPosition().set(posCurrent);
		
		/*
		 * Read joint first child velocity
		 */
		r = vrep.simxGetObjectVelocity(clientID, robotH.getValue(), velocityVRep, angularVelocityVRep, remoteApi.simx_opmode_buffer);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint velocity "+cart.getName()+", error : "+r);
        }
        velocity = (velocityVRep.getArray()[0] + velocityVRep.getArray()[1] + velocityVRep.getArray()[2]);
		
		/*
		 * Read joint force
		 */
		r = vrep.simxGetJointForce(clientID, jointH.getValue(), forceJointVRep, remoteApi.simx_opmode_buffer);
        if(r!=remoteApi.simx_return_ok && r!=remoteApi.simx_return_novalue_flag) {
        	System.out.println("ERROR Retriving joint position "+cart.getName()+", error : "+r);
        }
        
    	System.out.println("DEBUG Robot "+cart.getName()+
    			" pos: "+intFormat.format(cart.getPosition().get())+
    			", vel: "+floatFormat.format(velocity)+
    			", force: "+floatFormat.format(forceJointVRep.getValue())+
    			", state: "+cart.getMovement().get()
    			);
    	
    	
    	
    	/*
    	 * If impossible to move further -> stop
    	 */
		if(Math.abs(forceJointVRep.getValue()) > 40f)
			if(Math.abs(velocity) < 0.1f) {
				if(cart.getMovement().get() != MovementState.stopping)
					cart.stopHere();
		}
		
		if(cart.getMovement().get()==MovementState.stopping && Math.abs(velocity)<0.001f) {
			vrep.simxSetJointTargetVelocity(clientID, jointH.getValue(), 0f, remoteApi.simx_opmode_streaming);
			cart.getMovement().set(MovementState.stop);
		}
	}
	
}
