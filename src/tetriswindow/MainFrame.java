package tetriswindow;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;



public class MainFrame
{
	private GamePanel panel;
	
	public MainFrame()
	{
		JFrame frame = new JFrame("Tetris Resolver");
	    panel = new GamePanel();
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

	public GamePanel getPanel()
	{
		return panel;
	}
}