package unitn.aose.warehousesim.api;

public interface IListener<T> {
	
	void notifyChanged(T value);
	
}
