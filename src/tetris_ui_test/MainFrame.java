package tetris_ui_test;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	TetrisGamePanel mTetrisGlassPanel;
	PlayPausePanel mPlayPausePanel;
	NextPanel mNextPanel;
	PicturePanel mPicturePanel;
	ScorePanel mScorePanel;
	GameHelperPanel mGameHelperPanel;
	JButton mHelpButton;
	
	
	
	MainFrame()
	{
		super ("Igor's self solving Tetris");
		
		setLayout(new GridBagLayout());
		ImageDefinitions.initializePictures();
		mTetrisGlassPanel = new TetrisGamePanel();
		
		//mPlayPausePanel = new PlayPausePanel();
		//mNextPanel = new NextPanel();
		mGameHelperPanel = new GameHelperPanel();
		mPicturePanel = new PicturePanel();
		mScorePanel = new ScorePanel();
		mHelpButton = new JButton();
		
		
		
		
		
		componentLayout();
		
		//add (mTetrisGlassPanel, new GridBagConstraints());
		
		
		setSize(new Dimension(615,450));
		setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	
	public void componentLayout()
	{
		//setLayout (new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
	    //gc.fill = GridBagConstraints.NONE;
	    
	    
		
		
		// first row
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.weightx = .18;
		gc.weighty = .03;
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add(mScorePanel, gc);
		
		
		gc.gridx=1;
		gc.weightx=.12;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add (mGameHelperPanel, gc);
		
		
		/*gc.gridx =1;
		gc.weightx=.02;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(mPlayPausePanel, gc);
		
		gc.gridx =1;
		gc.weightx=.07;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(mNextPanel,gc);*/
		
		
		
		
		
		// second row 
		
		
		
		gc.gridx  = 0;
		gc.gridy = 1;
		
		gc.weightx=.18;
		gc.weighty=.18;
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		
		add (mPicturePanel, gc);
		
		
		gc.gridy = 1;
		gc.gridx =1;
		
		gc.weightx =.12;
	
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		
		add(mTetrisGlassPanel, gc);
		
		
		
		
		
		
		
	}

}
