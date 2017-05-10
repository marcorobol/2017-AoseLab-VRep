package unitn.aose.warehousesim.observable;

public class AreaStateMonitor extends java.util.Observable {

	/**
	 * Call this when the area state has changed.
	 * All the observers will be notified
	 */
	public void setChanged(){
		super.setChanged();
		this.notifyObservers();
	}
}
