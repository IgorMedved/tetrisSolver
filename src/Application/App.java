package Application;





import javax.swing.SwingUtilities;

import tetriswindow.GamePanel;
import tetriswindow.MainFrame;
import controller.Controller;
import model.Game;


public class App
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// intializing MVC
		GuiRunnable guiThread = new GuiRunnable();
		SwingUtilities.invokeAndWait(guiThread); // run user interface on UI Thread
		Controller controller = new Controller (); // initialize controller
		GamePanel gamePanel = guiThread.getFrameHandle().getPanel(); // panel inside the window view class
		gamePanel.setListeners(controller); // set listener for gui events (button clicks, key strokes, etc)
		Game game = new Game(); // main model class
		
		controller.setModel(game); // pass model to controller
		game.setModelListener(gamePanel);
		//game.setPlay(true);
		//game.startGame();
		
		
		 
		//controller.run();		// start game
		
		
	}
}
