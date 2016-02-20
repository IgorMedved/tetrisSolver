package tetriswindow;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;



public class Window
{
	private UserInterface panel;
	
	public Window()
	{
		JFrame frame = new JFrame("Tetris Resolver");
	    panel = new UserInterface();
	    frame.addWindowListener(
	      new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	          System.exit(0);
	          }
	        }
	      );
	    frame.getContentPane().add(panel,"Center");
	    frame.setSize(panel.getPreferredSize());
	    frame.setVisible(true);
	}

	public UserInterface getPanel()
	{
		return panel;
	}
}