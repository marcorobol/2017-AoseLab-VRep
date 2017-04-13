package unitn.aose.warehousesim.simulator;

import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import unitn.aose.warehousesim.api.IListener;
import unitn.aose.warehousesim.api.IWarehouse;
import unitn.aose.warehousesim.api.SimulationState;

public class SimulationGui {

   private Frame mainFrame;
   private IAdapter adapter;
   private IWarehouse warehouse;
   
   
   
   public SimulationGui(IAdapter adapter, IWarehouse warehouse){
	   this.adapter = adapter;
	   this.warehouse = warehouse;
	   mainFrame = new Frame("Warehouse Agent GUI");
	   mainFrame.setSize(500,100);
	   mainFrame.setLayout(new FlowLayout());
	   mainFrame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent windowEvent){
			   adapter.stop();
			   System.exit(0);
		   }
	   });
	   
	   Panel p = createTimerPanel();
	   mainFrame.add(p);
	   
	   mainFrame.setVisible(true);
   }
   
   public static String GetFormattedInterval(final long ms) {
	    long millis = ms % 1000;
	    long x = ms / 1000;
	    long seconds = x % 60;
	    x /= 60;
	    long minutes = x % 60;
	    x /= 60;
	    long hours = x % 24;
	
	    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	}
   
   public Panel createTimerPanel() {
	   Panel controlPanel = new Panel();
	   controlPanel.setLayout(new FlowLayout());
	   
	   //DurationFormatUtils.formatDuration(value, "HH:mm:ss.S")
	   
	   Label timer = new Label();
	   timer.setText("Timer: "+GetFormattedInterval(warehouse.getSimulationTime().get()));
	   warehouse.getSimulationTime().registerListener(new IListener<Long>() {
    	  public void notifyChanged(Long value) {
    		  
    		  timer.setText("Timer: "+GetFormattedInterval(value));
    	  }
	   });
	   
	   Label state = new Label();
	   state.setText(warehouse.getSimulationState().get().toString());
	   warehouse.getSimulationState().registerListener(new IListener<SimulationState>() {
		   public void notifyChanged(SimulationState value) {
			   state.setText(value.toString());
		   }
	   });

	   Button play = new Button("play");
	   play.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   adapter.play();
		   }
	   });
	   
	   Button pause = new Button("pause");
	   pause.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   adapter.pause();
		   }
	   });
	   
	   Button stop = new Button("stop");
	   stop.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   adapter.stop();
		   }
	   });
	   
	   
	   controlPanel.add(timer);
	   controlPanel.add(state);
	   controlPanel.add(play);
	   controlPanel.add(pause);
	   controlPanel.add(stop);
	   
	   mainFrame.setVisible(true);
      
	   return controlPanel;
	}
}