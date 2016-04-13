package tetris_ui_test;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
/* 
 * This is Top level UI class all the components are initialized and layed out here
 */
public class MainFrame extends JFrame {
	
	private TetrisGamePanel mTetrisGamePanel; // panel showing tetris board and all the figures on it (South East corner)
	private PicturePanel mPicturePanel; // panel with cover picture and hidden picture
	private ScorePanel mScorePanel; // panel showing current score and level
	private GameHelperPanel mGameHelperPanel; // panel with play button, and next shape Label and Panel on it 
	
	// not implemented
	JButton mHelpButton;
	
	
	
	MainFrame()
	{
		super ("Igor's self solving Tetris");
		
		ImageDefinitions.initializePictures(); // call to read-in all the UI pictures, 
		
		// UI coponent initialization
		mTetrisGamePanel = new TetrisGamePanel(); 
		mGameHelperPanel = new GameHelperPanel();
		mPicturePanel = new PicturePanel();
		mScorePanel = new ScorePanel();
		
		// not implemented
		//mHelpButton = new JButton();
		
		
		
		
		
		componentLayout();
		
		
		
		setSize(new Dimension(615,450));
		setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	
	public void componentLayout()
	{
		setLayout (new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
	    
	    
		
		
		// first row (scorePanel and GameHelperPanel
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
		
		
		
		// second row (PicturePanel and TetrisGamePanel
		
		
		
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
		
		add(mTetrisGamePanel, gc);
		
		
		
		
		
		
		
	}

}
