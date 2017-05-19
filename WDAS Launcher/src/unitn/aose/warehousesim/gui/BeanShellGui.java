package unitn.aose.warehousesim.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import unitn.aose.warehousesim.api.Logger;
import bsh.EvalError;
import bsh.Interpreter;

public class BeanShellGui {

   private Frame mainFrame;
   
   
   
   public BeanShellGui(Interpreter i, String example){
	   mainFrame = new Frame("BeanShell interactive GUI");
	   mainFrame.setSize(700,600);
	   mainFrame.setLayout(new GridLayout(2, 1));
	   mainFrame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent windowEvent){
			   System.exit(0);
		   }        
	   });
	   
	   
	   
	   Label header1 = new Label();
	   header1.setText("This is just a sample script from the guiscript.bsh file");
	   Label header2 = new Label();
	   header2.setText("Feel free to test your scripts here.\n");
	   Label header3 = new Label();
	   header3.setText("The file rootScript.bsh is always executed at start up.");
	   
	   
	   TextArea console = new TextArea();
	   console.setColumns(300);
	   console.setText(example);
	   
	   TextArea err = new TextArea();
	   err.setColumns(600);
	   

	   

		Thread beanShellThread = new Thread("beanshellInteractive") {
			public void run() {
				try {
					console.setEditable(false);
					i.eval(console.getText());
				} catch (EvalError e1) {
					StringWriter errors = new StringWriter();
					PrintWriter printer = new PrintWriter(errors);
					e1.printStackTrace(new PrintWriter(errors));
					printer.close();
					err.setText(errors.toString());
					e1.printStackTrace();
				}
				console.setEditable(true);
			}
		};
		
		
	   Button run = new Button("Execute");
	   run.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
				beanShellThread.start();
		   }
	   });
	   
	   Button kill = new Button("Kill");
	   kill.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
				beanShellThread.stop();
				console.setEditable(true);
		   }
	   });

	   mainFrame.add(console);
	   Panel buttonsPanel = new Panel();
	   buttonsPanel.add(header1);
	   buttonsPanel.add(header2);
	   buttonsPanel.add(header3);
	   buttonsPanel.add(run);
	   buttonsPanel.add(kill);
	   buttonsPanel.add(err);
	   mainFrame.add(buttonsPanel);
	   
	   mainFrame.setVisible(true);
   }
   
}