package test;

import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		remoteApi vrep = new remoteApi();
		
        int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
        
        IntW cubo = new IntW(1);
        IntW joint = new IntW(1);
        
        vrep.simxGetObjectHandle(clientID, "bob", cubo, vrep.simx_opmode_blocking);
        
        vrep.simxSetObjectPosition(clientID, cubo.getValue(), -1, new FloatWA(12), vrep.simx_opmode_blocking);
	}

}
