package unitn.aose.warehousesim.observable;

import unitn.aose.warehousesim.api.SimulationState;

public class ObservableSimulationState extends Observable<SimulationState, SimulationState> {

	private ObservableLong timer;
	
	public ObservableSimulationState(ObservableLong timer) {
		this.timer = timer;
		set(SimulationState.stopped);
	}
	
	@Override
	public void set(SimulationState value) {
		if(value==SimulationState.stopped)
			timer.set(0l);
		super.set(value);
	}
	
}
