package unitn.aose.warehousesim.observable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IObservableQueue;

public class ObservableQueue<T extends L, L> implements IObservableQueue<L> {
	
	List<IListener<IObservableQueue<L>>> listeners;
	LinkedList<T> queue;
	
	
	
	public ObservableQueue() {
		this.listeners = new ArrayList<IListener<IObservableQueue<L>>>();
		this.queue = new LinkedList<T>();
	}
	
	
	
	@Override
	public void registerListener(IListener<IObservableQueue<L>> listener) {
		listeners.add(listener);
	}

	@Override
	public void unregisterListener(IListener<IObservableQueue<L>> listener) {
		listeners.remove(listener);
	}
	
	
	
	public boolean offer(T e) {
		boolean result = queue.offer(e);
		for(Object o : listeners.toArray()) {
			IListener<IObservableQueue<L>> l = (IListener<IObservableQueue<L>>) o;
			l.notifyChanged(this);
		}
		return result;
	}
	
	@Override
	public T peek() {
		return queue.peek();
	}
	
	@Override
	public T poll() {
		return queue.poll();
	}
	
	@Override
	public void clear() {
		queue.clear();
	}
	
	@Override
	public boolean contains(L o) {
		return queue.contains(o);
	}
	
	@Override
	public int size() {
		return queue.size();
	}
	
}
