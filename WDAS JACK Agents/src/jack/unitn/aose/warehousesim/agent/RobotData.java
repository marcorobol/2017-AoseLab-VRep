package unitn.aose.warehousesim.agent;

import java.util.Observable;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ICartPerception;
import unitn.aose.warehousesim.api.ICross;
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
    private int movementState;
    private int loadUnloadState;
    private String areaRight, areaLeft;
    private ICross iCrossHaed, iCrossBehind, iCrossHere;
    private Float velocity;
    private BoxRef loadedBox;
    private String name;
    private BoxRef boxOnRight, boxOnLeft;
    public static final int MS_STOP=0, MS_RUNNINGFORWARD=1, MS_RUNNINGBACKWARD=2, MS_STOPPING=3;
    public static final int LUS_UNLOADED=0, LUS_LOADED=1, LUS_LOADINGLEFT=2, LUS_LOADINGRIGHT=3, LUS_UNLOADINGLEFT=4, LUS_UNLOADINGRIGHT=5;
    
    public RobotData(){}
    
    /*
     * Method to update the fields and notify when they changed
     */
    public void update(){
    	if(null==robot) return;
    	boxOnLeft=robot.getBoxOnLeft();
    	boxOnRight=robot.getBoxOnRight();
    	//velocity=(Float)robot.getVelocity();
    	loadedBox=robot.getLoadedBox();
    	name=robot.getName();
    	try{
	    	pos=(int)robot.getPosition().get();
	    	movementState=((MovementState)robot.getMovement().get()).ordinal();
	    	loadUnloadState=((LoadUnloadState)robot.getLoadUnload().get()).ordinal();
	    	areaLeft=((AreaRef)robot.getAreaOnLeft().get()).getName();
	    	areaRight=((AreaRef)robot.getAreaOnRight().get()).getName();
	    	iCrossHaed=(ICross)robot.getCrossHaed().get();
	    	iCrossBehind=(ICross)robot.getCrossBehind().get();
	    	iCrossHere=(ICross)robot.getCrossHere().get();
    	}catch(NullPointerException e){
    		System.err.println("ERROR: cannot update robot data "+robot+": "+e.getMessage());
    	}
    	
    	this.setChanged();
    	this.notifyObservers();
    }
    
      
    public void setRobot(IRobot r){
        robot = r;
        update();
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
	
	public int getMovement() {
		return movementState;
	}
	
	public int getLoadUnload() {
		return loadUnloadState;
	}
	
	public String getAreaOnLeft() {
		return areaLeft;
	}
	
	public String getAreaOnRight() {
		return areaRight;
	}
	
	public BoxRef getBoxOnLeft() {
		return boxOnLeft;
	}
	
	public BoxRef getBoxOnRight() {
		return boxOnRight;
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
	
	/**
	 * 
	 * @deprecated velocity is no updated
	 * @return
	 */
	public Float getVelocity() {
		return velocity;
	}
    
}
