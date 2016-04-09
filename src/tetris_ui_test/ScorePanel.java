package tetris_ui_test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	
	JLabel mTempLabel;
	
	ScorePanel ()
	{
		setLayout (new BorderLayout());
		setPreferredSize(new Dimension(180,40));
		
		mTempLabel = new JLabel("score and level");
		add (mTempLabel, BorderLayout.CENTER);
	}
	

}
