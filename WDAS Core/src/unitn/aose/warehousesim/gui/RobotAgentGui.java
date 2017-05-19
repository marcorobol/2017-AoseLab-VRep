package unitn.aose.warehousesim.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import unitn.aose.warehousesim.agent.IRobotAgent;

public class RobotAgentGui {

   private Frame mainFrame;
   
   
   
   public RobotAgentGui(Collection<IRobotAgent> agents){
	   mainFrame = new Frame("RobotAgent GUI");
	   mainFrame.setSize(900,400);
	   mainFrame.setLayout(new GridLayout(agents.size(), 1));
	   mainFrame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent windowEvent){
			   System.exit(0);
		   }        
	   });
	   
	   for(IRobotAgent r : agents)
		   mainFrame.add(createAgentPanel(r));
	   
	   mainFrame.setVisible(true);
   }
   
   
   public Panel createAgentPanel(IRobotAgent agent) {
	   Panel controlPanel = new Panel();
	   controlPanel.setLayout(new FlowLayout());
	   
	   Label name = new Label();
	   name.setText(agent.name());
	   
	   TextField position = new TextField();
	   position.setColumns(3);
	   
	   Button goToPosition = new Button("GOAL:goToPosition");
	   goToPosition.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   agent.goToPosition(new Integer(position.getText()));
		   }
	   });
	   
	   Button test = new Button("GOAL:test");
	   test.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   agent.testMethod();
		   }
	   });
	   
	   TextField methodName = new TextField();
	   methodName.setColumns(20);
	   
	   Button generic = new Button("GOAL:typeHereMethodName:");
	   generic.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   try {
				IRobotAgent.class.getMethod(methodName.getText()).invoke(agent, new Object[0]);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e1) {
				methodName.setText("invalidMethodName");
			}
			   agent.testMethod();
		   }
	   });
	   
	   controlPanel.add(name);
	   controlPanel.add(goToPosition);
	   controlPanel.add(position);
	   controlPanel.add(test);
	   controlPanel.add(generic);
	   controlPanel.add(methodName);
	   
	   mainFrame.setVisible(true);
      
	   return controlPanel;
	}
}