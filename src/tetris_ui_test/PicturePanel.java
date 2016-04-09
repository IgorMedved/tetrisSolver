package tetris_ui_test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PicturePanel extends JPanel{
	JLabel mPictureLabel;
	
	public PicturePanel()
	{
		setPreferredSize(new Dimension(360,360));
		setLayout(new BorderLayout());
		
		mPictureLabel = ImageDefinitions.getPicture('c');// 'c' - for cover picture
		
		add (mPictureLabel, BorderLayout.CENTER);
		setVisible(true);
	}

}
