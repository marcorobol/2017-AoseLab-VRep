package unitn.aose.warehousesim.api;


public interface IObservable<T> {
	
	T get();
	
	void registerListener(IListener<T> listener);
	void unregisterListener(IListener<T> listener);
}
