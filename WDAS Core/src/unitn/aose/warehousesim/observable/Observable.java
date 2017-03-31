package unitn.aose.warehousesim.observable;

import java.util.ArrayList;
import java.util.List;

import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IObservable;

public abstract class Observable<T extends L, L>  implements IObservable<L> {
	
	@SuppressWarnings("rawtypes")
	List<IListener> listeners = new ArrayList<IListener>();
	
	private T value = null;

	
	public Observable(T init) {
		this.value = init;
	}
	public Observable() {
	}
	
	public void set(T value) {
		if(this.value!=null && this.value.equals(value))
			return;
		this.value = value;
		for(Object o : listeners.toArray()) {
			@SuppressWarnings("unchecked")
			IListener<L> l = (IListener<L>) o;
			l.notifyChanged(value);
		}
	}
	
	@Override
	public T get() {
		return value;
	}
	
	@Override
	public void registerListener(IListener<L> listener) {
		listeners.add(listener);
	}
	
	@Override
	public void unregisterListener(IListener<L> listener) {
		listeners.remove(listener);
	}

}
