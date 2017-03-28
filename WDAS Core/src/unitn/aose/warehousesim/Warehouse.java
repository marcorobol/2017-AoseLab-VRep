package unitn.aose.warehousesim;

public class Warehouse {

	public static void run(IEnvironment env) throws InterruptedException {
		
//        env.createBoxIn(area_ac);
//        env.createBoxIn(area_bd);
		
        /*
         * Simulation cycle
         */
		
        long ms = System.currentTimeMillis();
        while(true) {
        	Thread.sleep(500);
        	System.out.println(System.currentTimeMillis()-ms+" ms");
        	ms = System.currentTimeMillis();
        	env.update();
        }

	}

}
