package unitn.aose.warehousesim.adapter.vrep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coppelia.FloatWA;
import coppelia.remoteApi;
import unitn.aose.warehousesim.IEnvironment;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Cart;

public class EnvironmentVRep implements IEnvironment {
	
	private remoteApi vrep;
	private int clientID;
	
    private List<RailVRep> rails = new ArrayList<RailVRep>();
	private Map<AreaRef, AreaVRep> areas = new HashMap<AreaRef, AreaVRep>();
    private Map<BoxRef, BoxVRep> boxes = new HashMap<BoxRef, BoxVRep>();
	private Map<CartRef, RobotVRep> robots = new HashMap<CartRef, RobotVRep>();
	
	
	
    public EnvironmentVRep(remoteApi vrep, int clientID) {
		this.vrep = vrep;
		this.clientID = clientID;
    }
    
    
	
	/*
	 * Define elements
	 */

	public RailVRep defineRail(String name, float total_lenght, int steps) {
		RailVRep r = new RailVRep(vrep, clientID, name, total_lenght, steps, this);
		rails.add(r);
		return r;
	}

	public RobotVRep defineRobot(String name, RailRef rail) {
		RobotVRep r = new RobotVRep(vrep, clientID, name, getRail(rail), this);
		robots.put(r, r);
		return r;
	}

	public AreaVRep defineArea(String name) {
		AreaVRep a = new AreaVRep(vrep, clientID, name, this);
		areas.put(a, a);
		return a;
	}

	public BoxVRep defineBox(String name) {
		BoxVRep b = new BoxVRep(vrep, clientID, name);
		boxes.put(b, b);
		return b;
	}
    
    
    
    /*
     * Get given reference
     */
    
	public AreaVRep getArea(AreaRef ref) {
		return areas.get(ref);
	}
	
	public BoxVRep getBox(BoxRef ref) {
		return boxes.get(ref);
	}

	public RailVRep getRail(RailRef ref) {
		for(RailVRep r : rails) {
			if(r==ref)
				return r;
		}
		return null;
	}
	
	@Override
	public RobotVRep getRobot(CartRef ref) {
		return robots.get(ref);
	}
	
	@Override
	public AreaVRep getTellerMachine(AreaRef area) {
		return getArea(area);
	}
	
	
	
	/*
	 * Get arrays
	 */
    
	@Override
	public Set<CartRef> getCarts() {
		return robots.keySet();
	}
	
	@Override
	public Set<AreaRef> getAreas() {
		return areas.keySet();
	}
	
	@Override
	public Set<BoxRef> getBoxes() {
		return boxes.keySet();
	}
	
	
	
	/*
	 * Create and delete boxes
	 */
	
	@Override
	public BoxVRep createBoxIn(AreaRef areaRef) {
		AreaVRep a = getArea(areaRef);
		for(BoxVRep b : boxes.values()) {
			if(b.getArea()==null && b.getRobot()==null) {
		    	/*
				 * Set parent
				 */
				int r = vrep.simxSetObjectParent(clientID, b.getHandle().getValue(),
						a.getHandle().getValue(), false, remoteApi.simx_opmode_blocking);
		        if(r!=remoteApi.simx_return_ok) {
		        	System.out.println("ERROR Setting parent of box " + b.getName() +". Error : "+r);
		        }
		        /*
		         * Move
		         */
				FloatWA posBoxStart = new FloatWA(3);
				posBoxStart.getArray()[0] = 0f;
				posBoxStart.getArray()[1] = 0f;
				posBoxStart.getArray()[2] = 0.3f;
				r = vrep.simxSetObjectPosition(clientID, b.getHandle().getValue(),
						remoteApi.sim_handle_parent, posBoxStart, remoteApi.simx_opmode_blocking);
		        if(r!=remoteApi.simx_return_ok) {
		        	System.out.println("ERROR Setting position of box " + b.getName() +". Error : "+r);
		        }
		        
				a.setBox(b);
				b.setArea(a);
				return b;
			}
		}
		return null;
	}



	@Override
	public void deleteBoxIn(AreaRef ref) {
		AreaVRep a = getArea(ref);
		BoxVRep b = getBox(a.getBox());
        /*
         * Move
         */
		FloatWA posBoxStart = new FloatWA(3);
		posBoxStart.getArray()[0] = 100f;
		posBoxStart.getArray()[1] = 100f;
		posBoxStart.getArray()[2] = 100f;
		int r = vrep.simxSetObjectPosition(clientID, b.getHandle().getValue(),
				remoteApi.sim_handle_all, posBoxStart, remoteApi.simx_opmode_streaming);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Setting position of box " + a.getBox().getName() +". Error : "+r);
        }
        
		a.setBox(null);
		b.setArea(null);
	}


	
	/*
	 * Update
	 */
	
	@Override
	public void update() {
    	for(RobotVRep r : robots.values()) {
    		r.update();
    	}
	}

}
