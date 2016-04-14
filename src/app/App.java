package app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import tetris_ui.MainFrame;

public class App {

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		GuiRunnable runnable = new GuiRunnable();
		
		SwingUtilities.invokeAndWait(runnable);
		
		
		

	}

}
