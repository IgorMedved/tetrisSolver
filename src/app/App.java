package app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import tetris_controller.Game;
import tetris_ui.MainFrame;

public class App {

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		GuiRunnable guiThread = new GuiRunnable();
		
		SwingUtilities.invokeAndWait(guiThread);
		
		Game game = new Game();
		MainFrame gameFrame = guiThread.getFrameHandle();
		gameFrame.setListener(game);
		game.setModelListener(gameFrame);
		
		
		

	}

}
