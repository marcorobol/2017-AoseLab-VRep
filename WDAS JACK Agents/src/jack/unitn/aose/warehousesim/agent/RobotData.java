package unitn.aose.warehousesim.agent;

import java.util.Observable;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.ICartPerception;
import unitn.aose.warehousesim.api.ICross;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.IRobotMonitor;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.MovementWithRespectToMe;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;

public class RobotData extends Observable {

	/**
	 * MovementState
	 */
	public static final int MS_STOP = 0, MS_RUNNINGFORWARD = 1, MS_RUNNINGBACKWARD = 2, MS_STOPPING = 3;

	public static int getMovementStatus(MovementState ms) {
		return ms.ordinal();
	}

	public static MovementState getMovementState(int ms) {
		switch (ms) {
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
	public static final int LUS_UNLOADED = 0, LUS_LOADED = 1, LUS_LOADINGLEFT = 2, LUS_LOADINGRIGHT = 3,
			LUS_UNLOADINGLEFT = 4, LUS_UNLOADINGRIGHT = 5;

	public static int getLoadUnloadState(LoadUnloadState lus) {
		return lus.ordinal();
	}

	public static LoadUnloadState getLoadUnloadState(int lus) {
		switch (lus) {
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
	public static final int MWR_GETTINGCLOSER = 0, MWR_GOINGAWAY = 1, MWR_STEADY = 2, MWR_UNKNOWN = 3;

	public static int getMovementWithRespectToMe(MovementWithRespectToMe mwr) {
		return mwr.ordinal();
	}

	public static MovementWithRespectToMe getMovementWithRespectToMe(int mwr) {
		switch (mwr) {
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
	 * Graphical representation assuming heading right (->) 7 3 5 1 > 0 6 2 4
	 */
	public static final int PWR_AHEAD = 0, PWR_BEHIND = 1, PWR_RIGHT = 2, PWR_LEFT = 3, PWR_AHEADRIGHT = 4,
			PWR_AHEADLEFT = 5, PWR_BEHINDRIGHT = 6, PWR_BEHINDLEFT = 7, PWR_UNKNOWN = 8;

	public static int getPositionWithRespectToMe(PositionWithRespectToMe pwr) {
		if (pwr.equals(PositionWithRespectToMe.haed)) {
			return PWR_AHEAD;
		} else if (pwr.equals(PositionWithRespectToMe.behind)) {
			return PWR_BEHIND;
		} else if (pwr.equals(PositionWithRespectToMe.right)) {
			return PWR_RIGHT;
		} else if (pwr.equals(PositionWithRespectToMe.left)) {
			return PWR_LEFT;
		} else if (pwr.equals(PositionWithRespectToMe.haedRight)) {
			return PWR_AHEADRIGHT;
		} else if (pwr.equals(PositionWithRespectToMe.haedLeft)) {
			return PWR_AHEADLEFT;
		} else if (pwr.equals(PositionWithRespectToMe.behindRight)) {
			return PWR_BEHINDRIGHT;
		} else if (pwr.equals(PositionWithRespectToMe.behindLeft)) {
			return PWR_BEHINDLEFT;
		} else {
			return PWR_UNKNOWN;
		}
	}

	public static PositionWithRespectToMe getPositionWithRespectToMe(int pwr) {
		switch (pwr) {
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
	public static final int AS_FREE = 0, AS_ELABORATINGDEPOSIT = 1, AS_ELABORATINGWITHDRAW = 2, AS_BOXAVAILABLE = 3, AS_UNKNOWN = 4;

	public static int getAreaState(AreaState as) {
		return as.ordinal();
	}

	public static AreaState getAreaState(int as) {
		switch (as) {
		case AS_FREE:
			return AreaState.free;
		case AS_ELABORATINGDEPOSIT:
			return AreaState.elaboratingDeposit;
		case AS_ELABORATINGWITHDRAW:
			return AreaState.elaboratingWithdraw;
		case AS_BOXAVAILABLE:
			return AreaState.boxAvailable;
		case AS_UNKNOWN:
		default:
			return AreaState.unknown;
		}
	}

	/**
	 * The wrapped robot object, this is set as soon as possible using
	 * setRobot(IRobot) method
	 */
	private IRobot robot;

	//// internal values
	private String name; // name of the robot
	private int pos; // position of the robot
	private int movementState; // actual movement of the robot
	private int loadUnloadState; // whether the robot is loaded or not, or it's
									// loading
	private String areaRight, areaLeft; // name of the area (left/right)
	private String boxOnRight, boxOnLeft; // name of the box (left/right)
	private String iCrossHaed, iCrossBehind, iCrossHere;
	private String loadedBox; // name of the loaded box on the robot

	private ICartPerception[] cartPerceptions; // a position based
												// cartPerception map

	public RobotData() {
		cartPerceptions = new ICartPerception[8];
	}

	/// single bit consts to track changed parameters
	private static final int CH_CARTP = 1 << 0, CH_BOXL = 1 << 1, CH_BOXR = 1 << 2, CH_LOADEDBOX = 1 << 3,
			CH_LUS = 1 << 4, CH_CROSS = 1 << 5, CH_AREAREF = 1 << 6;

	/**
	 * Internally used to safely check if 'a' is different from 'b' even if they
	 * are null
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static boolean hasChanged(Object a, Object b) {
		if (a == b) {
			return false;
		} else if (a == null && b != null) {
			return true;
		} else if (a != null && b == null) {
			return true;
		} else if (!a.equals(b)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method must be called as often as possible by the main loop (main
	 * thread), for example by the updateTime of an IRobotAgent If a robot is
	 * set, all the currently available robot data are collected and saved
	 * internally (snapshot), including the perception of other robots; the
	 * getters will retrieve such data, not the realtime data. If an actual
	 * change in the internal data is applied, the listeners (observers) will be
	 * notified.
	 */
	public void update() {
		if (null == robot)
			return;
		int changed = 0;
		// collect perception data
		for (int i = 0; i < 8; ++i) {
			CartRef c = (CartRef) robot.getCartAround(getPositionWithRespectToMe(i)).get();
			if (null != c) {
				ICartPerception cp = robot.getCartPerception(c);
				if (hasChanged(cartPerceptions[i], cp)) {
					cartPerceptions[i] = cp;
					changed |= CH_CARTP;
				}
			} else if (null != cartPerceptions[i]) {
				cartPerceptions[i] = null;
				changed |= CH_CARTP;
			}
		}

		// position and movement
		Object p = robot.getPosition().get();
		pos = (null != p) ? (int) p : 0;
		movementState = ((MovementState) robot.getMovement().get()).ordinal();

		// boxes around
		BoxRef b = (BoxRef) robot.getBoxOnLeft();
		if (null != b) {
			String n = b.getName();
			if (hasChanged(boxOnLeft, n)) {
				boxOnLeft = n;
				changed |= CH_BOXL;
			}
		} else {
			boxOnLeft = null;
		}
		b = (BoxRef) robot.getBoxOnRight();
		if (null != b) {
			String n = b.getName();
			if (hasChanged(boxOnRight, n)) {
				boxOnRight = n;
				changed |= CH_BOXR;
			}
		} else {
			boxOnRight = null;
		}
		b = (BoxRef) robot.getLoadedBox();
		if (null != b) {
			String n = b.getName();
			if (hasChanged(loadedBox, n)) {
				loadedBox = b.getName();
				changed |= CH_LOADEDBOX;
			}
		} else {
			loadedBox = null;
		}

		// areas
		AreaRef a = (AreaRef) robot.getAreaOnLeft().get();
		if (null != a) {
			String an = a.getName(); //an = name of the area
			if(hasChanged(areaLeft, an)){
				areaLeft = a.getName();
				changed |= CH_AREAREF;
			} 
		}else if(areaLeft!=null){
			areaLeft=null;
			changed |= CH_AREAREF;
		}
		
		a = (AreaRef) robot.getAreaOnRight().get();
		if (null != a) {
			String an = a.getName();
			if(hasChanged(areaRight, an)){
				areaRight = an;
				changed |= CH_AREAREF;
			}
		} else if(areaRight!=null){
				areaRight=null;
				changed |= CH_AREAREF;
			
		}

		// crosses
		ICross c = (ICross) robot.getCrossHaed().get();
		if (null != c) {
			String nc = c.getRail().getName(); //nc= name of the cross
			if (hasChanged(iCrossHaed, nc)) { 
				this.iCrossHaed = nc;	//if it's changed, iCrossHaed is nc
				changed |= CH_CROSS;  //it's changed
			}
		} else if (iCrossHaed != null) {
			this.iCrossHaed = null; //set iCrossAhed to null
			changed |= CH_CROSS; //it's changed
		}
		
		c = (ICross) robot.getCrossBehind().get();
		if (null != c) {
			String nc = c.getRail().getName();
			if (hasChanged(iCrossBehind, nc)) {
				this.iCrossBehind = nc;
				changed |= CH_CROSS;
			}
		} else if (iCrossBehind != null) {
			this.iCrossBehind = null;
			changed |= CH_CROSS;
		}
		
		c = (ICross) robot.getCrossHere().get();
		if (null != c) {
			String nc = c.getRail().getName();
			if (hasChanged(iCrossHere, nc)) {
				this.iCrossHere = nc;
				changed |= CH_CROSS;
			}
		} else if (iCrossHere != null) {
			this.iCrossHere = null;
			changed |= CH_CROSS;
		}

		

		// load status
		int l = getLoadUnloadState(((LoadUnloadState) robot.getLoadUnload().get()));
		if (l != loadUnloadState) {
			loadUnloadState = l;
			changed |= CH_LUS;
		}

		if (changed > 0) {
			this.setChanged();
			this.notifyObservers();
		}
	}

	public void setRobot(IRobot r) {
		robot = r;
		// robot name never change unless a new robot is assigned
		name = robot.getName();
		// update as soon as possible
		update();
	}

	/**
	 * The robot associated with this wrapper
	 * 
	 * @see setRobot(IRobot r)
	 * @return the robot instance associated with this wrapper or null if no
	 *         robot has been set yet
	 */
	public IRobotMonitor getRobot() {
		return robot;
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

	public String getRail() {
		return robot.getRail().getName();
	}

	public int getMovement() {
		return movementState;
	}

	public int getLoadUnload() {
		return loadUnloadState;
	}

	public String getAreaOnLeft() { // AreaRef -> getName()-> String
		return areaLeft;
	}

	public String getAreaOnRight() {
		return areaRight;
	}

	public String getBoxOnLeft() {
		return boxOnLeft;
	}

	public String getBoxOnRight() {
		return boxOnRight;
	}

	public int getAreaState(AreaRef area) {
		return getAreaState((AreaState) robot.getAreaState(area).get());
	}

	public String getCrossAhead() {
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

	public ICartPerception getCartPerception(int pwr) {
		return cartPerceptions[pwr];
	}

}
