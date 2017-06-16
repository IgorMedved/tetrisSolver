package app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import tetris_controller.Game;
import tetris_ui.MainFrame;

public class Run {

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		GuiRunnable guiThread = new GuiRunnable(); // runnable for initializing GUI
		SwingUtilities.invokeAndWait(guiThread); // initialize GUI
		Game game = new Game(); // Initialize main game controller
		MainFrame gameFrame = guiThread.getFrameHandle(); // User interface Main Frame
		gameFrame.setListener(game); // game controller listens and responds to button and keyboard clicks
		game.setModelListener(gameFrame); // mainFrame listens to updates in the model through the gameController
	}

}
