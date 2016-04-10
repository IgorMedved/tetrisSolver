package tetris_ui_test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScorePanel extends JPanel{
	
	JLabel mLevelLabel;
	JLabel mScoreLabel;
	JTextField mScoreText;
	JTextField mLevelText;
	
	ScorePanel ()
	{
		setLayout (new GridBagLayout());
		setPreferredSize(new Dimension(300,40));
		
		mLevelLabel = new JLabel("<html> <font color='blue'>Level: </font></html>");
		mScoreLabel = new JLabel("<html> <font color='blue'>Score: </font></html>");
		mLevelText = new JTextField(2);
		mScoreText = new JTextField(3);
		mLevelText.setBackground(new Color(40, 40, 255, 0));
		mLevelText.setEnabled(false);
		mLevelText.setText("10");
		mLevelText.setFont(new Font("Andalus", Font.BOLD, 18));
		
		mLevelText.setBorder(null);
		mLevelText.setDisabledTextColor(Color.BLUE);
		mLevelText.setEditable(true);
		
		
		mScoreText.setBackground(new Color(40, 40, 255, 0));
		mScoreText.setEnabled(false);
		mScoreText.setDisabledTextColor(Color.BLUE);
		mScoreText.setText("5300");
		mScoreText.setFont(new Font("Andalus", Font.PLAIN, 18));
		
		mScoreText.setBorder(null);
		
		//mLevelText.setForeground(new Color(0, 128, 128));
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx= 0;
		gc.gridy = 0;
		gc.weightx = 5;
		gc.anchor = GridBagConstraints.LINE_END;
		
		
		add (mLevelLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx=1;
		gc.weightx = 2;
		add (mLevelText, gc);
		
		
		
		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 2;
		gc.weightx = 5;
		add(mScoreLabel,gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx=3;
		gc.weightx = 2;
		add (mScoreText, gc);
		
		
		setBackground(new Color(40, 40, 255, 100));
		//mLevelLabel.setForeground(new Color(0, 128, 128));
		//mScoreLabel.setForeground(new Color(0,128,128));
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		mLevelLabel.setFont(new Font("Andalus", Font.BOLD, 18));
		mScoreLabel.setFont(new Font("Andalus", Font.BOLD, 18));
		//mLevelLabel.setBackground(Color.DARK_GRAY);
		mScoreLabel.setBackground(Color.DARK_GRAY);
		
		
		
	}
	

}
