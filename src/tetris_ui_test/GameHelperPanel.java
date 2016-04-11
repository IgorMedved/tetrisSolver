package tetris_ui_test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameHelperPanel extends JPanel{
	
	//private PlayPausePanel mPlayPausePanel;
	//private NextPanel mNextPanel;
	private JLabel mPlayLabel;
	private JLabel mPauseLabel;
	private JButton mPlayPauseButton;
	private NextFigurePanel mNextFigurePanel;
	private JLabel mNextFigureLabel;
	
	public GameHelperPanel()
	{
		//mPlayPausePanel = new PlayPausePanel();
		//mNextPanel = new NextPanel();
		setPreferredSize(new Dimension(244,55));
		
		
		setBorder (BorderFactory.createLineBorder(Color.BLUE,1));
		
		mNextFigureLabel = new JLabel("<html> <font color = blue>Next</font></html>");
		mNextFigureLabel.setFont(new Font("Andalus", Font.BOLD, 18));
		mNextFigurePanel = new NextFigurePanel();
		
		mPlayLabel = ImageDefinitions.getPicture('p');
		mPauseLabel = ImageDefinitions.getPicture('u');
		
		mPlayPauseButton = new JButton();
		mPlayPauseButton.setPreferredSize(new Dimension (40,40));
		mPlayPauseButton.setIcon(mPlayLabel.getIcon());
		mPlayPauseButton.setBorder(null);;
		
		mPlayPauseButton.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				firePlayPauseEvent();
			}
		});
		
		//componentLayout();
		
		layoutComponent();
		
		
	}
	
	public void componentLayout()
	{
		add (mPlayPauseButton, BorderLayout.NORTH);
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

		//add (mPlayLabel, "play");
		//add (mPauseLabel, "pause");
		//add(mPlayPauseButton, BorderLayout.WEST);
		

		//add (mPlayPausePanel, BorderLayout.WEST);
		//add (mNextPanel, BorderLayout.EAST);
		//add(mNextFigureLabel, BorderLayout.CENTER);
		//add(mNextFigurePanel, BorderLayout.EAST);
		setBackground(new Color(144, 208, 255, 255));
	}
	
	public void firePlayPauseEvent()
	{
		if (mPlayPauseButton.getIcon() == mPlayLabel.getIcon())
			mPlayPauseButton.setIcon(mPauseLabel.getIcon());
		else
			mPlayPauseButton.setIcon(mPlayLabel.getIcon());
		
	}

}
