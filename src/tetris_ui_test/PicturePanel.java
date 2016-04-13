package tetris_ui_test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.contracts.TetrisContract;

public class PicturePanel extends JPanel{
	JLabel mPictureLabel;
	
	public PicturePanel()
	{
		setPreferredSize(new Dimension(360,364));
		setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		setLayout(new BorderLayout());
		
		
		mPictureLabel = ImageDefinitions.getPictureAsLabel(TetrisContract.COVER);
		
		add (mPictureLabel, BorderLayout.CENTER);
		//setVisible(true);
	}

}
