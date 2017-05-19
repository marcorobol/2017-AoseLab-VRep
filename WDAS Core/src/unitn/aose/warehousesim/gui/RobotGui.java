package unitn.aose.warehousesim.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import unitn.aose.warehousesim.api.ICross;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IRobot;
import unitn.aose.warehousesim.api.LoadUnloadState;
import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.AreaRef;

public class RobotGui {

   private Frame mainFrame;
//   private Label headerLabel;
//   private Label statusLabel;
//   private Panel controlPanel;
   private List<IRobot> robotList;
   
   
   
   public RobotGui(List<IRobot> robotList){
	   this.robotList = robotList;
	   mainFrame = new Frame("Robot GUI");
	   mainFrame.setSize(1300,400);
	   mainFrame.setLayout(new GridLayout(robotList.size(), 1));
	   mainFrame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent windowEvent){
			   System.exit(0);
		   }        
	   });    
//	   headerLabel = new Label();
//	   headerLabel.setAlignment(Label.CENTER);
//	   headerLabel.setText("Robot: "+robot.getName()); 
//	   statusLabel = new Label();        
//	   statusLabel.setAlignment(Label.CENTER);
//	   statusLabel.setSize(350,100);
	   
//	   mainFrame.add(headerLabel);
	   for(IRobot r : robotList)
		   mainFrame.add(createRobotPanel(r));
//	   mainFrame.add(statusLabel);
	   mainFrame.setVisible(true);
   }
   
   
   public Panel createRobotPanel(IRobot robot) {
	   Panel controlPanel = new Panel();
	   controlPanel.setLayout(new FlowLayout());
	   
	   Label name = new Label();
	   name.setText(robot.getName());
	   
	   TextField movement = new TextField();
	   movement.setColumns(10);
	   movement.setEditable(false);
	   movement.setText(robot.getMovement().get().name());
	   robot.getMovement().registerListener(new IListener<MovementState>() {
    	  public void notifyChanged(MovementState value) {
    		  movement.setText(value.name());
    	  }
	   });
	   
	   TextField position = new TextField();
	   position.setColumns(12);
	   position.setEditable(false);
	   position.setText("Position:    ");
	   robot.getPosition().registerListener(new IListener<Integer>() {
    	  public void notifyChanged(Integer pos) {
    		  position.setText("Position: "+pos);
    	  }
	   });
	   
	   TextField loadUnload = new TextField();
	   loadUnload.setColumns(10);
	   loadUnload.setEditable(false);
	   loadUnload.setText(robot.getLoadUnload().get().name());
	   robot.getLoadUnload().registerListener(new IListener<LoadUnloadState>() {
    	  public void notifyChanged(LoadUnloadState value) {
    		  loadUnload.setText(value.name());
    	  }
	   });

	   Button moveForeward = new Button("moveForeward");
	   moveForeward.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   robot.moveForward();
		   }
	   });
	   
	   Button moveBackward = new Button("moveBackward");
	   moveBackward.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 robot.moveBackward();
		   }
	   });
	   
	   Button stop = new Button("stop");
	   stop.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 robot.stopHere();
		   }
	   });
	   
	   TextField areaLeft = new TextField();
	   areaLeft.setColumns(8);
	   areaLeft.setEditable(false);
	   areaLeft.setText("left:         ");
	   robot.getAreaOnLeft().registerListener(new IListener<AreaRef>() {
    	  public void notifyChanged(AreaRef value) {
    		  String box = (robot.getBoxOnLeft()!=null?robot.getBoxOnLeft().getName():"");
    		  areaLeft.setText("left:"+(value!=null?value.getName():"")+"["+box+"]");
    	  }
	   });
	   
	   TextField areaRight = new TextField();
	   areaRight.setColumns(8);
	   areaRight.setEditable(false);
	   areaRight.setText("right:         ");
	   robot.getAreaOnRight().registerListener(new IListener<AreaRef>() {
    	  public void notifyChanged(AreaRef value) {
    		  String box = (robot.getBoxOnRight()!=null?robot.getBoxOnRight().getName():"");
    		  areaRight.setText("right:"+(value!=null?value.getName():"")+"["+box+"]");
    	  }
	   });
	   
	   Button loadLeft = new Button("loadLeft");
	   loadLeft.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 robot.loadLeft();
		   }
	   });
	   
	   Button loadRight = new Button("loadRight");
	   loadRight.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 robot.loadRight();
		   }
	   });
	   
	   Button unloadLeft = new Button("unloadLeft");
	   unloadLeft.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 robot.unloadLeft();
		   }
	   });
	   
	   Button unloadRight = new Button("unloadRight");
	   unloadRight.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 robot.unloadRight();
		   }
	   });
	   
	   TextField cross = new TextField();
	   cross.setColumns(10);
	   cross.setEditable(false);
	   cross.setText("Cross:               ");
	   robot.getCrossHere().registerListener(new IListener<ICross>() {
		   public void notifyChanged(ICross value) {
			   cross.setText("Cross: "+(value!=null?value.getRail().getName():"--"));
		   }
	   });

	   
	   controlPanel.add(name);
	   controlPanel.add(movement);
	   controlPanel.add(position);
	   controlPanel.add(loadUnload);
	   controlPanel.add(moveForeward);
	   controlPanel.add(moveBackward);
	   controlPanel.add(stop);
	   controlPanel.add(areaLeft);
	   controlPanel.add(areaRight);
	   controlPanel.add(loadLeft);
	   controlPanel.add(loadRight);
	   controlPanel.add(unloadLeft);
	   controlPanel.add(unloadRight);
	   controlPanel.add(cross);
	   
	   mainFrame.setVisible(true);
      
	   return controlPanel;
	}
}