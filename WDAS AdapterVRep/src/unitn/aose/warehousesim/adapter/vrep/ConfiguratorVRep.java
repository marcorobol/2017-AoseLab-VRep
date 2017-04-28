package unitn.aose.warehousesim.adapter.vrep;

import unitn.aose.warehousesim.configuration.IConfigurator;
import unitn.aose.warehousesim.data.Area;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.Cart;
import unitn.aose.warehousesim.data.Rail;
import unitn.aose.warehousesim.simulator.Warehouse;

public class ConfiguratorVRep implements IConfigurator {

	private final Warehouse warehouse;
	private final AdapterVRep adapter;
	
	
	
    public ConfiguratorVRep(AdapterVRep adapter, Warehouse warehouse) {
		this.adapter = adapter;
		this.warehouse = warehouse;
    }
    
	
	
	@Override
	public Rail defineRail(String name, int steps) {
		Rail rail = warehouse.defineRail(name, steps);
		RailVRep r = adapter.defineRail(rail);
		return rail;
	}

	@Override
	public Cart defineCart(String name, Rail rail) {
		Cart cart = warehouse.defineCart(name, rail);
		CartVRep c = adapter.defineCart(cart);
		return cart;
	}

	@Override
	public Area defineArea(String name) {
		Area area = warehouse.defineArea(name);
		AreaVRep a = adapter.defineArea(area);
		return area;
	}

	@Override
	public Area defineDepositWithdrawArea(String name) {
		Area area = warehouse.defineDepositWithdrawArea(name);
		AreaVRep a = adapter.defineArea(area);
		return area;
	}

	@Override
	public Box defineBox(String name) {
		Box box = warehouse.defineBox(name);
		BoxVRep b = adapter.defineBox(box);
		return box;
	}
	
}
