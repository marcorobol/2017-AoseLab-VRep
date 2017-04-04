package unitn.aose.warehousesim.observable;

import unitn.aose.warehousesim.api.AreaState;

public class ObservableAreaState extends Observable<AreaState, AreaState> {

	public ObservableAreaState() {
		super();
		set(AreaState.free);
	}
}
