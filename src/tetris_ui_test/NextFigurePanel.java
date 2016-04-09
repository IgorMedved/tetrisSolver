package tetris_ui_test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NextFigurePanel extends JPanel{
	
	JLabel mTempLabel;
	
	NextFigurePanel()
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension (140,40));
		mTempLabel = new JLabel("nextFigure");
		
		add (mTempLabel, BorderLayout.CENTER);
	}

}
