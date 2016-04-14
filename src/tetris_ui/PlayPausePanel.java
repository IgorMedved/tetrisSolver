package tetris_ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.contracts.TetrisContract;

public class PlayPausePanel extends JPanel {
	
	private JLabel mPlayLabel;
	private JLabel mPauseLabel;
	private JButton mPlayPauseButton;
	
	
	PlayPausePanel()
	{
		//setLayout(new CardLayout());
		setPreferredSize(new Dimension(40,55));
		mPlayLabel = ImageDefinitions.getPictureAsLabel(TetrisContract.PLAY_BTN);
		mPauseLabel = ImageDefinitions.getPictureAsLabel(TetrisContract.PAUSE_BTN);
		
		mPlayPauseButton = new JButton();
		mPlayPauseButton.setIcon(mPlayLabel.getIcon());
		
		mPlayPauseButton.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				firePlayPauseEvent();
			}
		});
		
		
		//add (mPlayLabel, "play");
		//add (mPauseLabel, "pause");
		add(mPlayPauseButton, BorderLayout.NORTH);
		
	}
	
	public void firePlayPauseEvent()
	{
		if (mPlayPauseButton.getIcon() == mPlayLabel.getIcon())
			mPlayPauseButton.setIcon(mPauseLabel.getIcon());
		else
			mPlayPauseButton.setIcon(mPlayLabel.getIcon());
		
	}
	
	
	
	

}
