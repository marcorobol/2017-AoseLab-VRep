package unitn.aose.warehousesim.api;

import java.util.Date;
import unitn.aose.warehousesim.api.IObservable;
import unitn.aose.warehousesim.api.data.CartRef;
import unitn.aose.warehousesim.api.data.MovementWithRespectToMe;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;
import unitn.aose.warehousesim.api.data.RailRef;

public interface ICartPerception {
	
	Date getLastUpdate();
	
	CartRef getCart();
	
	RailRef getRail();
	
	IObservable<Boolean> getInFieldOfView();
	
	IObservable<MovementWithRespectToMe> getMovementWithRespectToMe();
	
	IObservable<PositionWithRespectToMe> getPositionWithRespectToMe();
	
}
