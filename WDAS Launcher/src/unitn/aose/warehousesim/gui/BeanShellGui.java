package unitn.aose.warehousesim.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import bsh.EvalError;
import bsh.Interpreter;

public class BeanShellGui {

   private Frame mainFrame;
   
   
   
   public BeanShellGui(Interpreter i, String example){
	   mainFrame = new Frame("BeanShell interactive GUI");
	   mainFrame.setSize(900,400);
	   mainFrame.setLayout(new GridLayout(4, 1));
	   mainFrame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent windowEvent){
			   System.exit(0);
		   }        
	   });

	   Label header = new Label();
	   header.setText("This is just a sample script. The rootScript is always executed at start.");
	   
	   TextArea console = new TextArea();
	   console.setColumns(300);
	   console.setText(example);
	   
	   TextArea err = new TextArea();
	   err.setColumns(300);
	   
	   Button run = new Button("Execute");
	   run.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   err.setText("");
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
	   });
	   
	   mainFrame.add(console);
	   mainFrame.add(run);
	   mainFrame.add(err);
	   
	   mainFrame.setVisible(true);
   }
   
}