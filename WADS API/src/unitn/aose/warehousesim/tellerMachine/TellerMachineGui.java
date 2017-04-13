package unitn.aose.warehousesim.tellerMachine;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.ITellerMachine;

public class TellerMachineGui {

   private Frame mainFrame;
//   private Label headerLabel;
//   private Label statusLabel;
//   private Panel controlPanel;
   private List<ITellerMachine> machineList;
   
   
   
   public TellerMachineGui(List<ITellerMachine> machineList){
	   this.machineList = machineList;
	   mainFrame = new Frame("Warehouse Agent GUI");
	   mainFrame.setSize(400,600);
	   mainFrame.setLayout(new GridLayout(machineList.size(), 1));
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
	   for(ITellerMachine r : machineList)
		   mainFrame.add(createMachinePanel(r));
//	   mainFrame.add(statusLabel);
	   mainFrame.setVisible(true);
   }
   
   
   public Panel createMachinePanel(ITellerMachine m) {
	   Panel controlPanel = new Panel();
	   controlPanel.setLayout(new FlowLayout());
	   
	   Label name = new Label();
	   name.setText(m.getName());
	   
	   Label state = new Label();
	   state.setText(m.getState().get().name());
	   Label requestedBox = new Label();
	   m.getState().registerListener(new IListener<AreaState>() {
    	  public void notifyChanged(AreaState value) {
    		  state.setText(value.name());
    		  requestedBox.setText((m.getRequestedBox()!=null?m.getRequestedBox().getName():"no requested box setted"));
    	  }
	   });
	   
	   Button drop = new Button("drop");
	   drop.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   m.createBox();
		   }
	   });
	   
	   Button collect = new Button("collect");
	   collect.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 m.removeBox();
		   }
	   });
	   
	   Button deposit = new Button("requestDeposit");
	   deposit.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
        	 m.requestDeposit();
		   }
	   });
	   
//	   Button withdraw = new Button("requestWithdraw");
//	   withdraw.addActionListener(new ActionListener() {
//		   public void actionPerformed(ActionEvent e) {
//        	 m.requestWithdraw(box);
//		   }
//	   });
	   
	   controlPanel.add(name);
	   controlPanel.add(state);
	   controlPanel.add(requestedBox);
	   controlPanel.add(drop);
	   controlPanel.add(collect);
	   controlPanel.add(deposit);
//	   controlPanel.add(withdraw);
	   
	   mainFrame.setVisible(true);
      
	   return controlPanel;
	}
}