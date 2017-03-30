package unitn.aose.warehousesim.api;

public interface IObservableQueue<T>  {
	
	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
	 * @return
	 */
	T peek();
	
	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue is empty.
	 * @return
	 */
	T poll();
	
	/**
	 * Remove everything.
	 */
	void clear();
	
	/**
	 * Check if it does contains o
	 * @param o
	 * @return
	 */
	boolean contains(T o);
	
	/**
	 * Return the number of the elements
	 * @return
	 */
	int size();
	
	void registerListener(IListener<IObservableQueue<T>> listener);
	void unregisterListener(IListener<IObservableQueue<T>> listener);
}
