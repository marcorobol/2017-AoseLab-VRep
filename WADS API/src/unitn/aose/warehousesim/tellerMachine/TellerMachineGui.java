package unitn.aose.warehousesim.tellerMachine;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import unitn.aose.warehousesim.api.AreaState;
import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.ITellerMachine;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.Logger;

public class TellerMachineGui {

   private Frame mainFrame;
//   private Label headerLabel;
//   private Label statusLabel;
//   private Panel controlPanel;
   private List<ITellerMachine> machineList;
   private IWarehouse warehouse;
   
   
   
   public TellerMachineGui(List<ITellerMachine> machineList, IWarehouse warehouse){
	   Logger.out.println("starting teller machine gui with "+machineList.size()+" machines");
	   this.machineList = machineList;
	   this.warehouse = warehouse;
	   mainFrame = new Frame("Warehouse Agent GUI");
	   mainFrame.setSize(600,600);
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
	   Label ticket = new Label();
	   m.getState().registerListener(new IListener<AreaState>() {
    	  public void notifyChanged(AreaState value) {
    		  state.setText(value.name());
    		  ticket.setText((m.getGeneratedTicket()!=null?m.getGeneratedTicket().getCode():"no ticket"));
    		  requestedBox.setText((m.getGeneratedTicket()!=null?m.getGeneratedTicket().getBox().getName():"no ticket"));
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

	   TextField boxName = new TextField();
	   boxName.setColumns(10);
	   
	   Button withdraw = new Button("requestWithdraw");
	   withdraw.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   if(warehouse.getBox(boxName.getText())!=null)
				   m.requestWithdraw(warehouse.getBox(boxName.getText()));
			   else
				   boxName.setText("invalidBoxName");
		   }
	   });
	   
	   controlPanel.add(name);
	   controlPanel.add(state);
	   controlPanel.add(ticket);
	   controlPanel.add(requestedBox);
	   controlPanel.add(drop);
	   controlPanel.add(collect);
	   controlPanel.add(deposit);
	   controlPanel.add(withdraw);
	   controlPanel.add(boxName);
	   
	   mainFrame.setVisible(true);
      
	   return controlPanel;
	}
}