package unitn.aose.warehousesim.agent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ICartPerception;
import unitn.aose.warehousesim.api.ICross;
import unitn.aose.warehousesim.api.IObservable;
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
	
	/**
	 * The wrapped robot object, this is set as soon as possible using setRobot(IRobot) method
	 */
    private IRobot robot;
    
    //// internal values   
    private String name; 					//name of the robot
    private int pos; 						//position of the robot
    private int movementState; 				//actual movement of the robot
    private int loadUnloadState; 			//whether the robot is loaded or not, or it's loading
    private String areaRight, areaLeft; 	//name of the area (left/right)
    private String boxOnRight, boxOnLeft; 	//name of the box (left/right)
    private String iCrossHaed, iCrossBehind, iCrossHere;
    private String loadedBox;				//name of the loaded box on the robot
    
    //perception			
    private ICartPerception[] cartPerceptions; //a position based cartPerception map
    
    /**
     * MovementState
     */
    public static final int 
    	MS_STOP=0, 
    	MS_RUNNINGFORWARD=1, 
    	MS_RUNNINGBACKWARD=2, 
    	MS_STOPPING=3;

    private static int getMovementStatus(MovementState ms){
    	return ms.ordinal();
    }
    
    private static MovementState getMovementState(int ms){
    	switch(ms){
    		case MS_STOP:
    			return MovementState.stop;
    		case MS_RUNNINGBACKWARD:
    			return MovementState.runningBackward;
    		case MS_RUNNINGFORWARD:
    			return MovementState.runningForward;
    		case MS_STOPPING:
    		default:
    			return MovementState.stopping;
    	}
    }
    
    /**
     * LoadUnloadState
     */
    public static final int 
    	LUS_UNLOADED=0, 
    	LUS_LOADED=1, 
    	LUS_LOADINGLEFT=2, 
    	LUS_LOADINGRIGHT=3, 
    	LUS_UNLOADINGLEFT=4, 
    	LUS_UNLOADINGRIGHT=5;
    
    public static int getLoadUnloadState(LoadUnloadState lus){
    	return lus.ordinal();
    }
    
    public static LoadUnloadState getLoadUnloadState(int lus){
    	switch(lus){
			case LUS_UNLOADED:
				return LoadUnloadState.unloaded;
			case LUS_LOADED:
				return LoadUnloadState.loaded;
			case LUS_LOADINGLEFT:
				return LoadUnloadState.loadingLeft;
			case LUS_LOADINGRIGHT:
				return LoadUnloadState.loadingRight;
			case LUS_UNLOADINGLEFT:
				return LoadUnloadState.unloadingLeft;
			case LUS_UNLOADINGRIGHT:
			default:
				return LoadUnloadState.unloadingRight;
    	}
	}
    
    /**
     * MovementWithRespectToMe
     */
    public static final int 
    	MWR_GETTINGCLOSER =0, 
    	MWR_GOINGAWAY=1, 
    	MWR_STEADY=2, 
    	MWR_UNKNOWN=3;
    
    public static int getMovementWithRespectToMe(MovementWithRespectToMe mwr){
    	return mwr.ordinal();
    }
    
    public static MovementWithRespectToMe getMovementWithRespectToMe(int mwr){
    	switch(mwr){
			case MWR_GETTINGCLOSER:
				return MovementWithRespectToMe.gettingCloser;
			case MWR_GOINGAWAY:
				return MovementWithRespectToMe.goingAway;
			case MWR_STEADY:
				return MovementWithRespectToMe.steady;
			case MWR_UNKNOWN:
			default:
				return MovementWithRespectToMe.unknown;
    	}
	}
    
    /**
     * PositionWithRespectToMe
     * 
     * Graphical representation assuming heading right (->)
     * 7  3  5
     * 1  >  0
     * 6  2  4
     */
    public static final int 
    	PWR_AHEAD=0, 
    	PWR_BEHIND=1, 
    	PWR_RIGHT=2, 
    	PWR_LEFT=3, 
    	PWR_AHEADRIGHT=4, 
    	PWR_AHEADLEFT=5, 
    	PWR_BEHINDRIGHT=6, 
    	PWR_BEHINDLEFT=7, 
    	PWR_UNKNOWN=8;
    
    public static int getPositionWithRespectToMe(PositionWithRespectToMe pwr){
    	if(pwr == PositionWithRespectToMe.haed){
    		return PWR_AHEAD;
    	} else if(pwr == PositionWithRespectToMe.behind){
    		return PWR_BEHIND;
    	} else if(pwr == PositionWithRespectToMe.right){
    		return PWR_RIGHT;
    	} else if(pwr == PositionWithRespectToMe.left){
    		return PWR_LEFT;
    	} else if(pwr == PositionWithRespectToMe.haedRight){
    		return PWR_AHEADRIGHT;
    	} else if(pwr == PositionWithRespectToMe.haedLeft){
    		return PWR_AHEADLEFT;
    	} else if(pwr == PositionWithRespectToMe.behindRight){
    		return PWR_BEHINDRIGHT;
    	} else if(pwr == PositionWithRespectToMe.behindLeft){
    		return PWR_BEHINDLEFT;
    	} else {
    		return PWR_UNKNOWN;
    	}
    }
    
    public static PositionWithRespectToMe getPositionWithRespectToMe(int pwr){
    	switch(pwr){
			case PWR_AHEAD:
				return PositionWithRespectToMe.haed;
			case PWR_BEHIND:
				return PositionWithRespectToMe.behind;
			case PWR_RIGHT:
				return PositionWithRespectToMe.right;
			case PWR_LEFT:
				return PositionWithRespectToMe.left;
			case PWR_AHEADRIGHT:
				return PositionWithRespectToMe.haedRight;
			case PWR_AHEADLEFT:
				return PositionWithRespectToMe.haedLeft;
			case PWR_BEHINDRIGHT:
				return PositionWithRespectToMe.behindRight;
			case PWR_BEHINDLEFT:
				return PositionWithRespectToMe.behindLeft;
			case PWR_UNKNOWN:
			default:
				return PositionWithRespectToMe.unknown;
    	}
	}
    
    /**
     * AreaState
     */
    public static final int
    	AS_FREE = 0,
    	AS_ELABORATINGDEPOSIT = 1,
    	AS_ELABORATINGWITHDRAW = 2,
    	AS_BOXAVAILABLE = 3;
    
    public static int getAreaState(AreaState as){
    	return as.ordinal();
    }
    
    public static AreaState getAreaState(int as){
    	switch(as){
			case AS_FREE:
				return AreaState.free;
			case AS_ELABORATINGDEPOSIT:
				return AreaState.elaboratingDeposit;
			case AS_ELABORATINGWITHDRAW:
				return AreaState.elaboratingWithdraw;
			case AS_BOXAVAILABLE:
			default:
				return AreaState.boxAvailable;
    	}
	}
    
    
    public RobotData()
    {
    	cartPerceptions = new ICartPerception[8];
    }
    
    /*
     * Method to update the fields and notify when they change
     */
    public void update(){
    	
    	if(null==robot) return;
    	//collect perception data
    	for(int i=0; i<8; ++i){
    		CartRef c = (CartRef)robot.getCartAround(getPositionWithRespectToMe(i)).get();
    		if(null != c){
    			System.out.println("adding cart perception "+i+" "+c.getName());
    			ICartPerception cp = robot.getCartPerception(c); 
    			cartPerceptions[i] = cp;
    		}else{
    			cartPerceptions[i] = null;
    		}
    	}
    	
    	try{
    		//position and movement
    		Object p = robot.getPosition().get(); 
    		pos = null != p ? (int)p : 0;
    		movementState=((MovementState)robot.getMovement().get()).ordinal();
    		
    		//boxes around
    		BoxRef b = (BoxRef)robot.getBoxOnLeft();
    		if(null != b){
    			boxOnLeft = b.getName();
    		}else{
    			boxOnLeft = null;
    		}
    		b = (BoxRef)robot.getBoxOnRight();
    		if(null != b){
    			boxOnRight = b.getName();
    		}else{
    			boxOnRight = null;
    		}
    		b = (BoxRef)robot.getLoadedBox();
    		if(null != b){
    			loadedBox = b.getName();
    		}else{
    			loadedBox = null;
    		}
    		
    		//load status
    		loadUnloadState=getLoadUnloadState(((LoadUnloadState)robot.getLoadUnload().get()));
    		
    		//areas around
    		AreaRef a = (AreaRef)robot.getAreaOnLeft().get();
    		if(null != a){
    			areaLeft = a.getName();
    		}else{
    			areaLeft = null;
    		}
    		a = (AreaRef)robot.getAreaOnRight().get();
    		if(null != a){
    			areaRight = a.getName();
    		}else{
    			areaRight = null;
    		}
    		
    		//crosses
    		ICross c = (ICross)robot.getCrossHaed().get();
    		if(null != c){
    			iCrossHaed = c.getRail().getName();
    		}else{
    			iCrossHaed = null;
    		}
    		c = (ICross)robot.getCrossBehind().get();
    		if(null != c){
    			iCrossBehind = c.getRail().getName();
    		}else{
    			iCrossBehind = null;
    		}
    		c = (ICross)robot.getCrossHere().get();
    		if(null != c){
    			iCrossHere = c.getRail().getName();
    		}else{
    			iCrossHere = null;
    		}
    	}catch(NullPointerException e){
    		System.err.println("ERROR: cannot update robot data "+name+": "+e.getMessage()); //previous fields null
    		e.printStackTrace();
    	}
    	
    	this.setChanged();
    	this.notifyObservers();
    }
    
      
    public void setRobot(IRobot r){
        robot = r;
        //robot name never change unless a new robot is assigned
    	name=robot.getName();
    	//update as soon as possible
        update();
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
	
	public int getAreaState(AreaRef area) {
		return getAreaState((AreaState)robot.getAreaState(area).get());
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
	
	public ICartPerception getCartPerception(int pwr){
		return cartPerceptions[pwr];
	}
    
}
