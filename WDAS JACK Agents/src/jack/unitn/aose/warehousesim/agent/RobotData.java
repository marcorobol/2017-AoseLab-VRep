package unitn.aose.warehousesim.agent;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ICartPerception;
import unitn.aose.warehousesim.api.ICross;
import unitn.aose.warehousesim.api.IListener;

import java.util.Observable;
import java.util.Observer;
import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;

public class RobotData extends Observable {
	
    private IRobot robot;
    private int pos;
    private MovementState movementState;
    private LoadUnloadState loadUnloadState;
    private AreaRef areaRefRight, areaRefLeft;
    private ICross iCrossHaed, iCrossBehind, iCrossHere;
    private Float velocity;
    private BoxRef loadedBox;
    private String name;
    
    public RobotData(){}
    
    /*
     * Method to update the fields and notify when they changed
     */
    public void update(){
    	pos=(int)robot.getPosition().get();
    	movementState=(MovementState)robot.getMovement().get();
    	loadUnloadState=(LoadUnloadState)robot.getLoadUnload().get();
    	areaRefLeft=(AreaRef)robot.getAreaOnLeft().get();
    	areaRefRight=(AreaRef)robot.getAreaOnRight().get();
    	iCrossHaed=(ICross)robot.getCrossHaed().get();
    	iCrossBehind=(ICross)robot.getCrossBehind().get();
    	iCrossHere=(ICross)robot.getCrossHere().get();
    	velocity=(Float)robot.getVelocity();
    	loadedBox=(BoxRef)robot.getLoadedBox();
    	name=(String)robot.getName();
    	
    	this.setChanged();
    	this.notifyObservers();
    }
    
      
    public void setRobot(IRobot r){
        robot = r;
    }
    
    // actuators ----->
	
	public void moveForward() {
		robot.moveForward();
	}
	
	public void moveBackward() {
		robot.moveBackward();
	}
	
	public void stopHere() {
		robot.stopHere();
	}
	
	public void loadLeft() {
		robot.loadLeft();
	}
	
	public void loadRight() {
		robot.loadLeft();
	}
	
	public void unloadLeft() {
		robot.unloadLeft();
	}
	
	public void unloadRight() {
		robot.unloadRight();
	}
	
	// getters ------>
	
	public MovementState getMovement() {
		return movementState;
	}
	
	public LoadUnloadState getLoadUnload() {
		return loadUnloadState;
	}
	
	public AreaRef getAreaOnLeft() {
		return areaRefLeft;
	}
	
	public AreaRef getAreaOnRight() {
		return areaRefRight;
	}
	
	public BoxRef getBoxOnLeft() {
		return robot.getBoxOnLeft();
	}
	
	public BoxRef getBoxOnRight() {
		return robot.getBoxOnRight();
	}
	
	public AreaState getAreaState(AreaRef area) {
		return (AreaState)robot.getAreaState(area).get();
	}
	
	public ICross getCrossHaed() {
		return iCrossHaed;
	}
	
	public ICross getCrossBehind() {
		return iCrossBehind;
	}
	
	public ICross getCrossHere() {
		return iCrossHere;
	}
	
	public CartRef getCartAround(PositionWithRespectToMe pos) {
		return (CartRef)robot.getCartAround(pos).get();
	}
	
	public ICartPerception getCartPerception(CartRef cart) {
		return robot.getCartPerception(cart);
	}
	
	public String getName() {
		return name;
	}
	
	public BoxRef getLoadedBox() {
		return loadedBox;
	}
	
	public int getPosition() {
		return pos;
	}
	
	public Float getVelocity() {
		return velocity;
	}
    
}
