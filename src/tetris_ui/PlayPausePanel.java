package tetris_ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tetris_model.contracts.TetrisContract;
import tetris_ui.events.PlayPanelEventListener;
import tetris_ui.events.UI_EventListener;

public class PlayPausePanel extends JPanel {
	
	private JLabel mPlayLabel;
	private JLabel mPauseLabel;
	private JButton mPlayPauseButton;
	
	private UI_EventListener mListener;
	
	
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
		if (mListener!= null)
			mListener.onPlayButtonPressed(null);
		
	}
	
	public void setPlayPanelEventListener (UI_EventListener listener)
	{
		mListener = listener;
	}
	
	
	
	

}
