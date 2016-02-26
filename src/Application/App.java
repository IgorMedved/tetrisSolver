package Application;





import tetriswindow.UserInterface;
import tetriswindow.Window;
import controller.Controller;
import model.Game;


public class App
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// intializing MVC
		Window window = new Window(); // top level view class
		Controller controller = new Controller (); // controller
		UserInterface gui = window.getPanel(); // panel inside the window view class
		gui.setListeners(controller); // set listener for gui events (button clicks, key strokes, etc)
		Game game = new Game(); // main model class
		
		controller.setModel(game); // pass model to controller
		game.setModelListener(gui);
		//game.setPlay(true);
		//game.startGame();
		
		
		 
		//controller.run();		// start game
		
		
	}
}
