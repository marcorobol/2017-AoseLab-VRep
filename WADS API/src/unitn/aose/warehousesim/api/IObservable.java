package unitn.aose.warehousesim.api;


public interface IObservable<T> {
	
	T get();
	
	/**
	 * To support the compatibility with native java observables
	 */
	java.util.Observable getMonitor();
	
	void registerListener(IListener<T> listener);
	void unregisterListener(IListener<T> listener);
}
