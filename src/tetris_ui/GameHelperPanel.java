package tetris_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tetris_model.Point;
import tetris_model.contracts.TetrisContract;
import tetris_ui.events.UI_EventListener;

// a panel in the top right corner showing play button and nextShape panel
public class GameHelperPanel extends JPanel {
	
	private Icon mPlayIcon;
	private Icon mPauseIcon;
	
	private boolean mShowReminder;
	
	private JButton mPlayPauseButton;
	private TetrisPanel mNextShapePanel;
	private JLabel mNextShapeLabel;
	
	
	
	public GameHelperPanel()
	{
		setPreferredSize(new Dimension(244,55));
		
		
		setBorder (BorderFactory.createLineBorder(Color.BLUE,1));
		
		mNextShapeLabel = new JLabel("<html> <font color = blue>Next</font></html>");
		mNextShapeLabel.setFont(new Font("Andalus", Font.BOLD, 18));
		mNextShapePanel = new TetrisPanel(4,2);
		
		mPlayIcon = ImageDefinitions.getIcon(TetrisContract.PLAY_BTN);
		mPauseIcon = ImageDefinitions.getIcon(TetrisContract.PAUSE_BTN);
		mShowReminder=true;
		
		
		mPlayPauseButton = new JButton();
		mPlayPauseButton.setPreferredSize(new Dimension (40,40));
		mPlayPauseButton.setIcon(mPlayIcon);
		mPlayPauseButton.setBorder(null);
		mPlayPauseButton.setName(TetrisContract.PLAY_BUTTON);
		layoutComponent();
	}
	
	
	
	public void layoutComponent()
	{
		setLayout (new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		
		add (mPlayPauseButton, gc);
		
		gc.gridx = 1;
		gc.weightx = 20;
		gc.anchor = GridBagConstraints.LINE_END;
		
		add (mNextShapeLabel, gc);
		
		
		gc.gridx = 2;
		gc.weightx = 4;
		gc.anchor = GridBagConstraints.LINE_END;
		
		add( mNextShapePanel, gc);

		setBackground(new Color(144, 208, 255, 255));
	}
	
	
	
	public void updateNextShape (List<List<Integer>> nextBoard, List<Point> shape, int shapeType)
	{
		mNextShapePanel.showBoard(nextBoard, shape, shapeType, null );
	}
	
	public void animateDeletedLines (List<Integer> deletedLines)
	{
		// this method not implemented
	}




	public void setGameOverMessage(boolean gameOver, boolean gameWon) {
		if (gameOver)
		{
			if (gameWon)
				JOptionPane.showMessageDialog(this, "Congratulations!!! You Won.");
			else
				JOptionPane.showMessageDialog(this, "Game Over!");
			
			mShowReminder = true;
		}
			
		
	}



	public void setPlayButton(boolean gamePlayed) {
		if( gamePlayed)
		{
			mPlayPauseButton.setIcon(mPauseIcon);
			
		}
		else
		{
			mPlayPauseButton.setIcon(mPlayIcon);
			if (mShowReminder)
				JOptionPane.showMessageDialog(this, "Press Play button to continue!");
			mShowReminder = false;
		}
				
		
	}



	public void setStartPlayMessage(boolean startGame) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setActionListener(ActionListener listener)
	{
		mPlayPauseButton.addActionListener(listener);
	}



	public void pressPlayButton() {
		mPlayPauseButton.doClick();
	}



	

}
