package test;

import coppelia.BoolW;
import coppelia.FloatW;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.IntWA;
import coppelia.remoteApi;

public class AppTestJoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		remoteApi vrep = new remoteApi();
		
        int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
        System.out.println("clientID = " + clientID);
        if(clientID != -1){
        	System.out.println("Connected to remote API server");
        }
        else{
        	
        	System.out.println("Failed connecting to remote API server" );
        }
        
        IntW bob = new IntW(1);
        vrep.simxGetObjectHandle(clientID, "bob#", bob, vrep.simx_opmode_blocking);
        //vrep.simxSetObjectPosition(clientID, bob.getValue(), -1, new FloatWA(12), vrep.simx_opmode_blocking);
        
        
        //Array output
        IntWA handles = new IntWA(0);
        
        //Numero di oggetti nella scena
        vrep.simxGetObjects(clientID, vrep.sim_handle_all, handles, vrep.simx_opmode_blocking);
        System.out.println("Numero di oggetti nella scena: " + handles.getLength());
        
        //Numero di oggetti selezionati nella scena
        vrep.simxGetObjectSelection(clientID, handles, vrep.simx_opmode_blocking);
        System.out.println("Numero di oggetti selezionati nella scena: " + handles.getLength());
        
        //Recupero e stampo la posizione di Bob  
        FloatWA position = new FloatWA(0);       
        vrep.simxGetObjectPosition(clientID, bob.getValue(), -1, position, vrep.simx_opmode_blocking);             
        float pippo[] =  position.getArray(); 	//Recupero l'array di Float
        System.out.println("La Posizione assoluta di Bob è:");
        for (float a : pippo) {
        	System.out.println(a);  
        }
        
         /*NON SO COME CAMBIARE I VALORI DEL FLOATWA POSITION
        position.getNewArray(3);
        pippo =  position.getArray();
        position.getArray()[0] = -0.59999937f;
        position.getArray()[1] = 0.07499953f;
        pippo[2] = (float) 0.12499999;
        */
        
 
        vrep.simxSetObjectPosition(clientID, bob.getValue(), -1, position, vrep.simx_opmode_blocking);
        
        
        
      //Recupero e stampo la velocità di Bob
        FloatWA velocity = new FloatWA(0);
        vrep.simxGetObjectVelocity(clientID, bob.getValue(), velocity, null, vrep.simx_opmode_blocking);
        pippo =  velocity.getArray(); 	//Recupero l'array di Float
        System.out.println("\nLa Velocità lineare di Bob è:");
        for (float a : pippo) {
        	System.out.println(a);  
        }
        
        
        /*Setto la velocità dei joint
        IntW leftjoint = new IntW(0);
        IntW rightjoint = new IntW(0);
        vrep.simxGetObjectHandle(clientID, "remoteApiControlledBubbleRobLeftMotor", leftjoint, vrep.simx_opmode_blocking);
        vrep.simxGetObjectHandle(clientID, "remoteApiControlledBubbleRobRightMotor", rightjoint, vrep.simx_opmode_blocking);
        vrep.simxSetJointTargetVelocity(clientID, leftjoint.getValue(), 0, vrep.simx_opmode_blocking);
        vrep.simxSetJointTargetVelocity(clientID, rightjoint.getValue(), 0, vrep.simx_opmode_blocking);
        */
        
       
        
        
        System.out.println("stampa prova");
        //Prova da codice c++
        
        int leftMotorHandle;
        int rightMotorHandle;
        int sensorHandle;
        
        IntW proximitySensor = new IntW(1);
        IntW leftMotor = new IntW(1);
        IntW rightMotor = new IntW(1);
        
        
        
        leftMotorHandle = vrep.simxGetObjectHandle(clientID, "remoteApiControlledBubbleRobLeftMotor", leftMotor, vrep.simx_opmode_blocking);
        rightMotorHandle = vrep.simxGetObjectHandle(clientID, "remoteApiControlledBubbleRobRightMotor", rightMotor, vrep.simx_opmode_blocking);
        sensorHandle = vrep.simxGetObjectHandle(clientID, "remoteApiControlledBubbleRobSensingNose", proximitySensor, vrep.simx_opmode_blocking);
        
        System.out.print("left motor: " + leftMotorHandle);
        System.out.print("right motor: " + rightMotorHandle);
        
        int driveBackStartTime=-99000;
        float[] motorSpeeds = new float[3];
        
        while (vrep.simxGetConnectionId(clientID)!=-1){
        	//System.out.println("dentro ciclo while");
        	BoolW sensorTrigger= new BoolW(false);
        	//if(vrep.simxReadProximitySensor(clientID, sensorHandle, sensorTrigger, null, null, null, vrep.simx_opmode_streaming) == vrep.simx_return_ok){
        		// We succeeded at reading the proximity sensor
        		int simulationTime = vrep.simxGetLastCmdTime(clientID);
        		if (simulationTime-driveBackStartTime<3000)
                { // driving backwards while slightly turning:
                    motorSpeeds[0]=-3.14f;
                    motorSpeeds[1]=-3.14f;
                }
        		else
                { // going forward:
        			motorSpeeds[0]=3.14f;
                    motorSpeeds[1]=3.14f;
                    if (sensorTrigger.getValue())
                        driveBackStartTime=simulationTime; // We detected something, and start the backward mode
                }
        		vrep.simxSetJointTargetVelocity(clientID,leftMotorHandle,motorSpeeds[0],vrep.simx_opmode_oneshot);
        		vrep.simxSetJointTargetVelocity(clientID,rightMotorHandle,motorSpeeds[1],vrep.simx_opmode_oneshot); 
        	//}
        }
        vrep.simxFinish(clientID);
        	
        	
	}

}
