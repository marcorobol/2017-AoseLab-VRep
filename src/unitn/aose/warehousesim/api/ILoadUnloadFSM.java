package unitn.aose.warehousesim.api;


public interface ILoadUnloadFSM {
	
	LoadUnloadState getState();
	
	void registerListener(ILoadUnloadListener listener);
	void unregisterListener(ILoadUnloadListener listener);
}
