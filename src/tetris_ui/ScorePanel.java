package tetris_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tetris_model.contracts.TetrisContract;

public class ScorePanel extends JPanel{
	
	JLabel mLevelLabel;
	JLabel mScoreLabel;
	JTextField mScoreText;
	JTextField mLevelText;
	
	
	
	ScorePanel ()
	{
		setLayout (new GridBagLayout());
		setPreferredSize(new Dimension(360,55));
		
		// field initialization
		mLevelLabel = new JLabel("<html> <font color='blue'>Level: </font></html>");
		mScoreLabel = new JLabel("<html> <font color='blue'>Score: </font></html>");
		mLevelText = new JTextField(2);
		mScoreText = new JTextField(3);
		
		
		// format mLevelTextField
		mLevelText.setText("1");
		mLevelText.setBackground(TetrisContract.LIGHT_BLUE);
		mLevelText.setEnabled(false);
		mLevelText.setFont(new Font("Andalus", Font.PLAIN, 18));
		mLevelText.setBorder(null);
		mLevelText.setDisabledTextColor(Color.BLUE);
//		mLevelText.setEditable(false);
		
		
		mScoreText.setText("0");
		mScoreText.setBackground(TetrisContract.LIGHT_BLUE);
		mScoreText.setEnabled(false);
		mScoreText.setDisabledTextColor(Color.BLUE);
		mScoreText.setFont(new Font("Andalus", Font.PLAIN, 18));
		mScoreText.setBorder(null);
		
		
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
		
		
		setBackground(TetrisContract.LIGHT_BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		mLevelLabel.setFont(new Font("Andalus", Font.BOLD, 18));
		mScoreLabel.setFont(new Font("Andalus", Font.BOLD, 18));
		mScoreLabel.setBackground(Color.DARK_GRAY);
		
		
		
	}



	public void updateLevel(int level)
	{
		mLevelText.setText(Integer.toString(level));
		
	}



	public void updateScore(int score)
	{
		mScoreText.setText(Integer.toString(score));
		
	}
	

}
