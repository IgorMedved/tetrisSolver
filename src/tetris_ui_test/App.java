package tetris_ui_test;

import java.io.File;
import java.nio.file.FileSystems;

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				new MainFrame();
			}
		});
		
		

	}

}
