package unitn.aose.warehousesim.adapter.vrep;

import java.util.HashMap;
import java.util.Map;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.data.AreaRef;
import unitn.aose.warehousesim.api.data.BoxRef;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.RailRef;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Cart;
import unitn.aose.warehousesim.data.Rail;
import unitn.aose.warehousesim.simulator.IAdapter;
import unitn.aose.warehousesim.simulator.IAdapterCart;

public class AdapterVRep implements IAdapter {
	
	private final remoteApi vrep;
	private final int clientID;
	
    private final Map<Rail, RailVRep> rails = new HashMap<Rail, RailVRep>();
	private final Map<Area, AreaVRep> areas = new HashMap<Area, AreaVRep>();
    private final Map<Box, BoxVRep> boxes = new HashMap<Box, BoxVRep>();
	private final Map<Cart, CartVRep> carts = new HashMap<Cart, CartVRep>();
	
	
	
    public AdapterVRep(remoteApi vrep, int clientID) {
		this.vrep = vrep;
		this.clientID = clientID;
		
		/*
		 * Configure VRep in sync mode
		 */
		vrep.simxSynchronous(clientID, true);
    }
    
    
	
	/*
	 * Factories
	 */

	public RailVRep defineRail(Rail rail) {
		RailVRep r = new RailVRep(vrep, clientID, rail, this);
		rails.put(rail, r);
		return r;
	}

	public CartVRep defineCart(Cart cart) {
		CartVRep c = new CartVRep(vrep, clientID, cart, this);
		carts.put(cart, c);
		return c;
	}

	public AreaVRep defineArea(Area area) {
		AreaVRep a = new AreaVRep(vrep, clientID, area, this);
		areas.put(area, a);
		return a;
	}

	public BoxVRep defineBox(Box box) {
		BoxVRep b = new BoxVRep(vrep, clientID, box);
		boxes.put(box, b);
		return b;
	}
    
    
    
    /*
     * Getter
     */
    
	public CartVRep getCart(CartRef ref) {
		return carts.get(ref);
	}
    
	public AreaVRep getArea(AreaRef ref) {
		return areas.get(ref);
	}
	
	public BoxVRep getBox(BoxRef ref) {
		return boxes.get(ref);
	}

	public RailVRep getRail(RailRef ref) {
		return rails.get(ref);
	}
	
	
	
	@Override
	public IAdapterCart getAdapterCart(CartRef cart) {
		return carts.get(cart);
	}
	
	
	
	/*
	 * Create and delete boxes
	 */
	
	@Override
	public Box createBoxIn(AreaRef areaRef) {
		for(Box box : boxes.keySet()) {
			BoxVRep b = boxes.get(box);
			if(b.isDeleted()) {
				AreaVRep a = getArea(areaRef);
		    	/*
				 * Set parent
				 */
				int r = vrep.simxSetObjectParent(clientID, b.getHandle().getValue(),
						a.getHandle().getValue(), false, remoteApi.simx_opmode_blocking);
		        if(r!=remoteApi.simx_return_ok) {
		        	System.out.println("ERROR Setting parent of box " + box.getName() +". Error : "+r);
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
		        	System.out.println("ERROR Setting position of box " + box.getName() +". Error : "+r);
		        }
		        
		        b.setDeleted(false);
		        
				return box;
			}
		}
		return null;
	}



	@Override
	public void deleteBox(BoxRef ref) {
		BoxVRep b = getBox(ref);
        /*
         * Move
         */
		FloatWA posBoxStart = new FloatWA(3);
		posBoxStart.getArray()[0] = 100f;
		posBoxStart.getArray()[1] = 100f;
		posBoxStart.getArray()[2] = 100f;
		int r = vrep.simxSetObjectPosition(clientID, b.getHandle().getValue(),
				remoteApi.sim_handle_parent, posBoxStart, remoteApi.simx_opmode_oneshot);
        if(r!=remoteApi.simx_return_ok) {
        	System.out.println("ERROR Setting position of box " + ref.getName() +". Error : "+r);
        }
        
        b.setDeleted(true);
	}


	
	/*
	 * Simulation
	 */
	
	private IntW remoteState = new IntW(0);
	@Override
	public void update(IWarehouse warehouse) {
		/*
		 * Update simulation state
		 */
		vrep.simxGetInMessageInfo(clientID, vrep.simx_headeroffset_server_state, remoteState);
//		System.out.println(remoteState.getValue());
		int i = remoteState.getValue();
		boolean[] bits = new boolean[3];
		bits[0] = Math.floor(i/1)%2 != 0;
		bits[1] = Math.floor(i/2)%2 != 0;
		bits[2] = Math.floor(i/4)%2 != 0;
		System.out.println("" + bits[0] + bits[1] + bits[2] );
		if(!bits[0])
			warehouse.stop();
		else if(bits[1])
			warehouse.pause();
		else if(bits[0])
			warehouse.play();
		
		/*
		 * Update robots values
		 */
		for(CartVRep r : carts.values()) {
    		r.update();
    	}
		
	}

	private IntW remoteTime = new IntW(0);
	@Override
	public long getSeverTime() {
		vrep.simxGetInMessageInfo(clientID, vrep.simx_headeroffset_server_time, remoteTime);
		return remoteTime.getValue();
	}
	
	@Override
	public void play() {
		vrep.simxStartSimulation(clientID, remoteApi.simx_opmode_oneshot);
	}
	
	@Override
	public void stop() {
		vrep.simxStopSimulation(clientID, remoteApi.simx_opmode_oneshot);
	}
	
	@Override
	public void pause() {
		vrep.simxPauseSimulation(clientID, remoteApi.simx_opmode_oneshot);
	}
	
	@Override
	public void synchronousTrigger() {
		vrep.simxSynchronousTrigger(clientID);
	}
	
}
