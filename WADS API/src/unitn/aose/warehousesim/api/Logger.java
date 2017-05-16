package unitn.aose.warehousesim.api;


public class Logger {

	public static Logger out = new Logger(new ILogger() {
		public void println(String text) {
			System.out.println(text);
		};
	});
	
	public static Logger err = new Logger(new ILogger() {
		public void println(String text) {
			System.out.println("ERROR "+text);
		};
	});
	
	private static interface ILogger {
		void println(String text);
	}
	
	private ILogger ilogger;
	
	public Logger(ILogger ilogger) {
		this.ilogger = ilogger;
	}
	
	public void println(String text) {
		ilogger.println(text);
	}
	
}
