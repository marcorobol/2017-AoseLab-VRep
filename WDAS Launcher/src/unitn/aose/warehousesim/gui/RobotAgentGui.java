package unitn.aose.warehousesim.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

import unitn.aose.warehousesim.agent.IRobotAgent;

public class RobotAgentGui {

   private Frame mainFrame;
   
   
   
   public RobotAgentGui(Collection<IRobotAgent> agents){
	   mainFrame = new Frame("Warehouse Agent GUI");
	   mainFrame.setSize(300,400);
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
	   
	   Button goToPosition = new Button("goToPosition");
	   goToPosition.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   agent.goToPosition(new Integer(position.getText()));
		   }
	   });
	   
	   Button test = new Button("test");
	   test.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   agent.testMethod();
		   }
	   });
	   
	   controlPanel.add(name);
	   controlPanel.add(position);
	   controlPanel.add(goToPosition);
	   controlPanel.add(test);
	   
	   mainFrame.setVisible(true);
      
	   return controlPanel;
	}
}