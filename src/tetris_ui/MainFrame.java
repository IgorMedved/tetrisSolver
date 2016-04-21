package tetris_ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import tetris_model.contracts.TetrisContract;
import tetris_model.events.GameEvent;
import tetris_model.events.GameEventListener;
import tetris_ui.events.UI_EventListener;
import tetris_ui.events.ShapeMoveAction;

/* 
 * This is Top level UI class all the components are initialized and layed out here
 */
public class MainFrame extends JFrame implements GameEventListener, ActionListener {
	
	private TetrisGamePanel mTetrisGamePanel; // panel showing tetris board and all the figures on it (South East corner)
	private PicturePanel mPicturePanel; // panel with cover picture and hidden picture
	private ScorePanel mScorePanel; // panel showing current score and level
	private GameHelperPanel mGameHelperPanel; // panel with play button, and next shape Label and Panel on it 
	
	
	//*************************************************************************///
	private UI_EventListener mIntefaceEventListener;
	
	// not implemented
	private JButton mHelpButton;
	
	
	
	
	
	public MainFrame()
	{
		super ("Igor's self solving Tetris");
		
		ImageDefinitions.initializePictures(); // call to read-in all the UI pictures, 
		
		// UI component initialization
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


	@Override
	public synchronized void gameEventOccurred(GameEvent e)
	{
		
		if (e.getBoard()!=null)
		{
			mTetrisGamePanel.showBoard(e.getBoard());
		}
		if (e.getNextShape()!= null)
			mGameHelperPanel.updateNextShape(e.getNextShape());
		
		if(e.getLevel()!= GameEvent.NOT_UPDATED)
			mScorePanel.updateLevel(e.getLevel());
			
		if (e.getScore() != GameEvent.NOT_UPDATED)
			mScorePanel.updateScore(e.getScore());
		
		if (e.getLinesDeleted()!= null)
			mGameHelperPanel.animateDeletedLines(e.getLinesDeleted());
		if (e.getCoverOpacity()!= GameEvent.NOT_UPDATED);
			mPicturePanel.updateCoverTransparency(e.getCoverOpacity());
		
		if (e.isGameOverUpdated())
			mGameHelperPanel.setGameOverMessage(e.isGameOver());
		
		if (e.isGamePlayedUpdated())
			mGameHelperPanel.setPlayButton(e.isGamePlayed());
		
		if (e.isGameStartedUpdated())
			mGameHelperPanel.setStartPlayMessage(e.isStartGame());
	}
	
	// method for binding up, down, left, and right keyboard keys to game Actions
	private void registerKeyActions()
	{
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.ROTATE_KEY), ShapeMoveAction.ACTN_ROTATE);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.LEFT_KEY), ShapeMoveAction.ACTN_LEFT);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.RIGHT_KEY), ShapeMoveAction.ACTN_RIGHT);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.DROP_KEY), ShapeMoveAction.ACTN_DROP);
		
		
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_ROTATE, new ShapeMoveAction(ShapeMoveAction.ACTN_ROTATE,mIntefaceEventListener));
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_LEFT, new ShapeMoveAction(ShapeMoveAction.ACTN_LEFT,mIntefaceEventListener));
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_RIGHT, new ShapeMoveAction(ShapeMoveAction.ACTN_RIGHT,mIntefaceEventListener)); 
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_DROP, new ShapeMoveAction(ShapeMoveAction.ACTN_DROP,mIntefaceEventListener));
	}
	
	
	// setting controller as UI_EventListener
	public void setListener (UI_EventListener listener)
	{
		mIntefaceEventListener = listener;
		mGameHelperPanel.setActionListener(this);
		registerKeyActions();
	}
	
	@Override
	public void actionPerformed(ActionEvent ev)
	{
		if (mIntefaceEventListener!=null)
		{
			JButton source = (JButton)ev.getSource(); // the source of action performed should be buttons only if it changes the code here would need to be modified
		
			if (source.getName().equals(TetrisContract.PLAY_BUTTON))
				mIntefaceEventListener.onPlayButtonPressed(null);
		}
		
	}

}
