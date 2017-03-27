package unitn.aose.warehousesim.observable;

import unitn.aose.warehousesim.api.LoadUnloadState;

public class ObservableLoadUnload extends Observable<LoadUnloadState, LoadUnloadState> {

	public ObservableLoadUnload(LoadUnloadState init) {
		super(init);
	}
	
}
