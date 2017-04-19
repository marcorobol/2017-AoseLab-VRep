package unitn.aose.warehousesim.agent;

import java.util.Date;
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
import unitn.aose.warehousesim.api.data.MovementWithRespectToMe;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;
import unitn.aose.warehousesim.api.data.RailRef;

public class RobotData extends Observable {
	
    private IRobot robot;
    private String name; 					//name of the robot
    private int pos; 						//position of the robot
    private int movementState; 				//actual movement of the robot
    private int loadUnloadState; 			//whether the robot is loaded or not, or it's loading
    private String areaRight, areaLeft; 	//name of the area (left/right)
    private String boxOnRight, boxOnLeft; 	//name of the box (left/right)
    private String iCrossHaed, iCrossBehind, iCrossHere;
    private Float velocity;					//velocity of the robot
    private String loadedBox;				//name of the loaded box on the robot
    
    private ICartPerception cartP;
    private Date lastUpdate;				
    private String cart;					//name of the cart around me
    private String rail;					//name of the rail
    private int movementWithRes;			//
    
    /*
     * Transform the enum in int
     */
    public static final int MS_STOP=0, MS_RUNNINGFORWARD=1, MS_RUNNINGBACKWARD=2, MS_STOPPING=3;
    public static final int LUS_UNLOADED=0, LUS_LOADED=1, LUS_LOADINGLEFT=2, LUS_LOADINGRIGHT=3, LUS_UNLOADINGLEFT=4, LUS_UNLOADINGRIGHT=5;
    public static final int MWR_gettingCloser =0, MWR_goingAway=1, MWR_steady=2, MWR_unknown=3;
    
    public RobotData(){}
    
    /*
     * Method to update the fields and notify when they change
     */
    public void update(){
    	
    	if(null==robot) return;
    	CartRef cr = (CartRef)robot.getCartAround(PositionWithRespectToMe.behind).get();
    	if(cr!=null){
    		cartP = robot.getCartPerception(cr);
    		System.out.println("------------ BEHIND = " + cr.getName());
    	}
    	else {
    		System.out.println("------------ CR NULL --------- ");
    	}
    	
    	name=robot.getName(); //name never change
    	
    	try{
    		/*pos=(int)robot.getPosition().get(); 									
	    	movementState=((MovementState)robot.getMovement().get()).ordinal(); 	
    		boxOnLeft=((BoxRef)robot.getBoxOnLeft()).getName(); 					
    		boxOnRight=((BoxRef)robot.getBoxOnRight()).getName();					
	    	loadUnloadState=((LoadUnloadState)robot.getLoadUnload().get()).ordinal(); 
	    	areaLeft=((AreaRef)robot.getAreaOnLeft().get()).getName();				
	    	areaRight=((AreaRef)robot.getAreaOnRight().get()).getName();			
	    	iCrossHaed=(String)((ICross)robot.getCrossHaed().get()).getRail().getName();
	    	iCrossBehind=(String)((ICross)robot.getCrossBehind().get()).getRail().getName();
	    	iCrossHere=(String)((ICross)robot.getCrossHere().get()).getRail().getName();
	    	loadedBox=((BoxRef)robot.getLoadedBox()).getName();	*/
	    	
	    	/*lastUpdate=cartP.getLastUpdate();
	    	cart=((CartRef)cartP.getCart()).getName();
	    	rail=((RailRef)cartP.getRail()).getName();
	    	*/
	    	
    	}catch(NullPointerException e){
    		System.err.println("ERROR: cannot update robot data "+robot.getName()+": "+e.getMessage()); //previous fields null
    	}
    	
    	this.setChanged();
    	this.notifyObservers();
    }
    
      
    public void setRobot(IRobot r){
        robot = r;
        update();
    }
    
    
    /*
     * NEW METHODS
     */
    
    public Date getLastUpdate(){
    	return lastUpdate;
    }
    
    public String getCart(){
    	return cart;    	
    }
    
    public String getRail(){
    	return rail;
    }
    
    /*
     * END
     */
    
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
	
	public String getAreaOnLeft() { //AreaRef -> getName()-> String
		return areaLeft;
	}
	
	public String getAreaOnRight() {
		return areaRight;
	}
	
	public String getBoxOnLeft() { //BoxRef -> String
		return boxOnLeft;
	}
	
	public String getBoxOnRight() {
		return boxOnRight;
	}
	
	public AreaState getAreaState(AreaRef area) {
		return (AreaState)robot.getAreaState(area).get();
	}
	
	public String getCrossHaed() {
		return iCrossHaed;
	}
	
	public String getCrossBehind() {
		return iCrossBehind;
	}
	
	public String getCrossHere() {
		return iCrossHere;
	}
		
	public String getName() {
		return name;
	}
	
	public String getLoadedBox() {
		return loadedBox;
	}
	
	public int getPosition() {
		return pos;
	}
	
	/**
	 * Velocity no longer exists
	 * @deprecated velocity is no updated
	 * @return
	 */
	public Float getVelocity() {
		return velocity;
	}
	
	/*
	 * Methods that cannot be updated in update()
	 */
	public CartRef getCartAround(PositionWithRespectToMe pos) { 
		return (CartRef)robot.getCartAround(pos).get();
	}
	
	public ICartPerception getCartPerception(CartRef cart) {
		return robot.getCartPerception(cart);
	}
    
}
