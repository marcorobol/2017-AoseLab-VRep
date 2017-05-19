package unitn.aose.warehousesim.api;

import java.util.Observable;

public class ObservableWrapper<T> extends Observable {
	
    public ObservableWrapper(IObservable<T> myObservable) {
		myObservable.registerListener(new IListener<T>() {
			public void notifyChanged(T value) {
				setChanged();
				notifyObservers();
			}
		});
	}
    
}
