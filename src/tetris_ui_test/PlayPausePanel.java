package tetris_ui_test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayPausePanel extends JPanel {
	
	private JLabel mTempLabel;
	
	PlayPausePanel()
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(40,40));
		mTempLabel = new JLabel("PlayButton");
		
		add (mTempLabel, BorderLayout.CENTER);
	}

}
