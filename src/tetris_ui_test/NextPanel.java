package tetris_ui_test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NextPanel extends JPanel {
	
	NextFigurePanel mNextFigurePanel;
	JLabel mNextFigureLabel;
	
	NextPanel()
	{
		mNextFigureLabel = new JLabel("<html> <font color = orange>Next</font></html>");
		mNextFigureLabel.setFont(new Font("ARIAL", Font.BOLD, 18));
		mNextFigurePanel = new NextFigurePanel();
		setPreferredSize(new Dimension(140,55));
		
		add(mNextFigureLabel, BorderLayout.CENTER);
		add(mNextFigurePanel, BorderLayout.EAST);
		
	}

}
