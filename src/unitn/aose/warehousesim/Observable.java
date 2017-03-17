package unitn.aose.warehousesim;

import java.util.ArrayList;
import java.util.List;

import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IObservable;

public class Observable<T> implements IObservable<T> {
	
	List<IListener<T>> listeners = new ArrayList<IListener<T>>();
	
	private T value = null;

	
	public Observable(T init) {
		this.value = init;
	}
	public Observable() {
	}
	
	public void set(T value) {
		this.value = value;
		for(Object l : listeners.toArray()) {
			((IListener<T>)l).notifyChanged(value);
		}
	}
	
	@Override
	public T get() {
		return value;
	}


	@Override
	public void registerListener(IListener<T> listener) {
		listeners.add(listener);
	}

	@Override
	public void unregisterListener(IListener<T> listener) {
		listeners.remove(listener);
	}

}
