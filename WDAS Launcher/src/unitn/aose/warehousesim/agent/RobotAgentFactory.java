package unitn.aose.warehousesim.agent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import unitn.aose.warehousesim.api.IRobot;

/**
 * 
 * @author Martina
 * 
 */

public class RobotAgentFactory {
	private String agentClassName;
	
	/**
	 * 
	 * @param agentClassName
	 */
	public RobotAgentFactory(String agentClassName){
		this.agentClassName = agentClassName;
	}
	/**
	 * Create an IRobotAgent 
	 * @param IRobot
	 * @return the created IRobotAgent, null in case of an error
	 */
	public IRobotAgent createAgent(IRobot robot){
		IRobotAgent ra=null;
		System.out.println("DEBUG creating agent for "+robot.getName());
		try {
			Class<?> raclass = Class.forName(agentClassName);
			Constructor<?> rac = raclass.getConstructor(IRobot.class);
			ra  = (IRobotAgent) rac.newInstance(robot);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch(ClassCastException e){
			e.printStackTrace();
		} catch(NoClassDefFoundError e){
			e.printStackTrace();
		}
		return ra;
	}
}
