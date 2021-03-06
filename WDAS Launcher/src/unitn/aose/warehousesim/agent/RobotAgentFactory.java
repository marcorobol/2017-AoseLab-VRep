package unitn.aose.warehousesim.agent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.IWarehouseMonitor;

/**
 * 
 * @author Martina
 * 
 */

public class RobotAgentFactory {
	private String agentClassName, coordinatorAgentClassName;
	
	/**
	 * 
	 * @param agentClassName
	 */
	public RobotAgentFactory(String agentClassName, String coordinatorAgentClassName){
		this.agentClassName = agentClassName;
		this.coordinatorAgentClassName = coordinatorAgentClassName;
	}
	/**
	 * Create an IRobotAgent 
	 * @param robot 
	 * @return the created IRobotAgent, null in case of an error
	 */
	public IRobotAgent createAgent(IRobot robot){
		IRobotAgent ra=null;
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
	
	public IWarehouseAgent createAgent(IWarehouseMonitor warehouse){
		IWarehouseAgent ra=null;
		try {
			Class<?> raclass = Class.forName(coordinatorAgentClassName);
			Constructor<?> rac = raclass.getConstructor(IWarehouseMonitor.class);
			ra  = (IWarehouseAgent) rac.newInstance(warehouse);
			
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
