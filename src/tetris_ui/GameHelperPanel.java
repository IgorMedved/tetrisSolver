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
import javax.swing.JPanel;

import model.contracts.TetrisContract;

public class GameHelperPanel extends JPanel{
	
	private Icon mPlayIcon;
	private Icon mPauseIcon;
	
	private JButton mPlayPauseButton;
	private NextFigurePanel mNextFigurePanel;
	private JLabel mNextFigureLabel;
	
	public GameHelperPanel()
	{
		setPreferredSize(new Dimension(244,55));
		
		
		setBorder (BorderFactory.createLineBorder(Color.BLUE,1));
		
		mNextFigureLabel = new JLabel("<html> <font color = blue>Next</font></html>");
		mNextFigureLabel.setFont(new Font("Andalus", Font.BOLD, 18));
		mNextFigurePanel = new NextFigurePanel();
		
		mPlayIcon = ImageDefinitions.getIcon(TetrisContract.PLAY_BTN);
		mPauseIcon = ImageDefinitions.getIcon(TetrisContract.PAUSE_BTN);
		
		
		mPlayPauseButton = new JButton();
		mPlayPauseButton.setPreferredSize(new Dimension (40,40));
		mPlayPauseButton.setIcon(mPlayIcon);
		mPlayPauseButton.setBorder(null);;
		
		mPlayPauseButton.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				firePlayPauseEvent();
			}
		});
		
		
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
		
		add (mNextFigureLabel, gc);
		
		
		gc.gridx = 2;
		gc.weightx = 4;
		gc.anchor = GridBagConstraints.LINE_END;
		
		add( mNextFigurePanel, gc);

		setBackground(new Color(144, 208, 255, 255));
	}
	
	public void firePlayPauseEvent()
	{
		if (mPlayPauseButton.getIcon() == mPlayIcon)
			mPlayPauseButton.setIcon(mPauseIcon);
		else
			mPlayPauseButton.setIcon(mPlayIcon);
		
	}
	
	public void updateNextShape (List<List<Integer>> nextShapeBoard)
	{
		mNextFigurePanel.showBoard(nextShapeBoard);
	}
	
	public void animateDeletedLines (List<Integer> deletedLines)
	{
		// this method not implemented
	}

}
