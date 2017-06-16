package tetris_ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.text.TextAction;

import tetris_model.Point;
import tetris_model.contracts.TetrisContract;
import tetris_model.events.GameEvent;
import tetris_model.events.GameEventListener;
import tetris_ui.events.UI_EventListener;
import tetris_ui.events.KeyBoardShortcutsAction;
import tetris_ui.events.ShapeMoveAction;

/* 
 * This is Top level UI class all the components are initialized and laid out here
 */
public class MainFrame extends JFrame implements GameEventListener, ActionListener, ItemListener {
	
	private TetrisPanel mTetrisGamePanel; // panel showing tetris board and all the figures on it (South East corner)
	private PicturePanel mPicturePanel; // panel with cover picture and hidden picture
	private ScorePanel mScorePanel; // panel showing current score and level
	private GameHelperPanel mGameHelperPanel; // panel with play button, and next shape Label and Panel on it 
	private SettingsPanel mSettingsPanel; // hide-able panel with some extra settings
	//private JSplitPane mSplitPane; // allows to hide the settings menu
	
	//*************************************************************************///
	private UI_EventListener mInterfaceEventListener;
	
	
	
	
	
	
	
	public MainFrame()
	{
		super ("Igor's self solving Tetris");
		
		ImageDefinitions.initializePictures(); // call to read-in all the UI pictures, 
		
		// UI component initialization
		mTetrisGamePanel = new TetrisPanel(12,18); 
		mGameHelperPanel = new GameHelperPanel();
		mPicturePanel = new PicturePanel();
		mScorePanel = new ScorePanel();
		mSettingsPanel = new SettingsPanel();
		componentLayout();
		setSize(new Dimension(615,490));
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
		gc.weighty = .04;
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
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add (mPicturePanel, gc);
		
		gc.gridy = 1;
		gc.gridx =1;
		gc.weightx =.12;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(mTetrisGamePanel, gc);
		
		// last row
		gc.gridx = 0;
		gc.gridy=2;
		gc.gridwidth=2;
		gc.weighty = .04;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(mSettingsPanel, gc);
	}

// update interface into response to model changes
	@Override
	public synchronized void gameEventOccurred(GameEvent e)
	{
		// update gameBoard view 
		if (e.getBoard()!=null)
		{
			//System.out.println("running board update");
			mTetrisGamePanel.showBoard(e.getBoard(), e.getCurrentShape(), e.getCurrentShapeType(), e.getHintsLocations());
			
		}
		// update nextShape screen if necessary
		if (e.getNextShape()!= null)
		{
			mGameHelperPanel.updateNextShape(e.getNextShapeBoard(), e.getNextShape(), e.getNextShapeType());
		}
		// update the game level showing on screen
		if(e.getLevel()!= GameEvent.NOT_UPDATED)
			mScorePanel.updateLevel(e.getLevel());
		// update score showing on the screen if necessary	
		if (e.getScore() != GameEvent.NOT_UPDATED)
			mScorePanel.updateScore(e.getScore());
		// show disappearing lines animation (not implemented)
		if (e.getLinesDeleted()!= null)
			mGameHelperPanel.animateDeletedLines(e.getLinesDeleted());
		// update picture cover transparency if necessary
		if (e.getCoverOpacity()!= GameEvent.NOT_UPDATED);
			mPicturePanel.updateCoverTransparency(e.getCoverOpacity());
		// show game over or you won messsage, when necessary
		if (e.isGameOverUpdated())
			mGameHelperPanel.setGameOverMessage(e.isGameOver(), e.isGameWon());
		// remind user to press play button and change the appearance of the play/pause button
		if (e.isGamePlayedUpdated())
			mGameHelperPanel.setPlayButton(e.isGamePlayed());
	}
	
	// method for binding up, down, left, and right keyboard keys to game Actions
	private void registerKeyActions()
	{
		// connect key presses to actions
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.ROTATE_KEY), ShapeMoveAction.ACTN_ROTATE);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.LEFT_KEY), ShapeMoveAction.ACTN_LEFT);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.RIGHT_KEY), ShapeMoveAction.ACTN_RIGHT);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(ShapeMoveAction.DROP_KEY), ShapeMoveAction.ACTN_DROP);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyBoardShortcutsAction.PLAY_PAUSE_KEY), KeyBoardShortcutsAction.ACTN_PLAY_PAUSE);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyBoardShortcutsAction.RESTART_GAME_KEY), KeyBoardShortcutsAction.ACTN_RESTART);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyBoardShortcutsAction.SHOW_HELP_KEY), KeyBoardShortcutsAction.ACTN_SHOW_HELP);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyBoardShortcutsAction.SHOW_HINTS_KEY), KeyBoardShortcutsAction.ACTN_SHOW_HINTS);
		((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyBoardShortcutsAction.PLAY_AI_MODE_KEY), KeyBoardShortcutsAction.ACTN_AI_MODE);
		
		// connect arrow key actions to game events
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_ROTATE, new ShapeMoveAction(ShapeMoveAction.ACTN_ROTATE,mInterfaceEventListener));
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_LEFT, new ShapeMoveAction(ShapeMoveAction.ACTN_LEFT,mInterfaceEventListener));
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_RIGHT, new ShapeMoveAction(ShapeMoveAction.ACTN_RIGHT,mInterfaceEventListener)); 
		((JComponent) getContentPane()).getActionMap().put(ShapeMoveAction.ACTN_DROP, new ShapeMoveAction(ShapeMoveAction.ACTN_DROP,mInterfaceEventListener));
		
		// connect short cut keys to interface button presses
		((JComponent) getContentPane()).getActionMap().put(KeyBoardShortcutsAction.ACTN_PLAY_PAUSE, new TextAction(KeyBoardShortcutsAction.ACTN_PLAY_PAUSE){

			@Override
			public void actionPerformed(ActionEvent e) {
				mGameHelperPanel.pressPlayButton();
				
			}
			
		});
		((JComponent) getContentPane()).getActionMap().put(KeyBoardShortcutsAction.ACTN_RESTART, new TextAction(KeyBoardShortcutsAction.ACTN_RESTART){

			@Override
			public void actionPerformed(ActionEvent e) {
				mSettingsPanel.pressRestartButton();
				
			}
			
		});
		((JComponent) getContentPane()).getActionMap().put(KeyBoardShortcutsAction.ACTN_SHOW_HELP, new TextAction(KeyBoardShortcutsAction.ACTN_SHOW_HELP){
			@Override
			public void actionPerformed(ActionEvent e) {
				mSettingsPanel.pressHelpButton();
			}
		});
		((JComponent) getContentPane()).getActionMap().put(KeyBoardShortcutsAction.ACTN_SHOW_HINTS, new TextAction(KeyBoardShortcutsAction.ACTN_SHOW_HINTS){
			@Override
			public void actionPerformed(ActionEvent e) {
				mSettingsPanel.pressShowHintsButton();
			}
		});
		((JComponent) getContentPane()).getActionMap().put(KeyBoardShortcutsAction.ACTN_AI_MODE, new TextAction(KeyBoardShortcutsAction.ACTN_AI_MODE){
			@Override
			public void actionPerformed(ActionEvent e) {
				mSettingsPanel.pressAIModeButton();
			}
		});
	}
	
	
	// setting controller as UI_EventListener
	public void setListener (UI_EventListener listener)
	{
		mInterfaceEventListener = listener;
		mGameHelperPanel.setActionListener(this); // connect play button clicks to this Frame
		mSettingsPanel.setActionListener (this); // connect restart and help button clicks to this Frame
		mSettingsPanel.setItemListener(this); // connect show hints and auto mode toggle button clicks to this frame
		registerKeyActions(); // register key bindings
	}
	
	@Override
	// this function processes JButton clicks
	// The button include Play button, restart button, help buttons
	public void actionPerformed(ActionEvent ev)
	{
		if (mInterfaceEventListener!=null)
		{
			JButton source = (JButton)ev.getSource(); // the source of action performed should be buttons only
			// if the code is  changed this needs to be modified
		
			if (source.getName().equalsIgnoreCase(TetrisContract.PLAY_BUTTON))
			{
				mInterfaceEventListener.onPlayButtonPressed(null);
			}
			else if (source.getName().equalsIgnoreCase(TetrisContract.HELP_BUTTON))
			{
				mInterfaceEventListener.onHelpButtonPressed();
				showHelp();
			}
			else if (source.getName().equalsIgnoreCase(TetrisContract.RESTART_BUTTON))
			{
				int confirmed = JOptionPane.showConfirmDialog(this, "Do you want to restart the game");
				if (confirmed == JOptionPane.YES_OPTION)
				{
					mInterfaceEventListener.initializeNewGame();
				}
					
			}
			
		}
		
	}

    // Dialog shown when help button is pressed
	private void showHelp() {
		StringBuilder sb = new StringBuilder ("Help for Tetris: ");
		sb.append("\nTetris is an easy game!");
		sb.append("\nWhen the game starts different shapes consisting of 4 squares");
		sb.append("\nstart falling to the bottom of the game board");
		sb.append("\n\nBy moving shapes left and right with corresponding arrow keys " );
		sb.append("\nand rotating it with \"Up\" key move shape into desired position");
		sb.append("\nand then drop it with \"Down\" key");
		sb.append("\n\n\nWhen you fill an entire row all the squares in this row");
		sb.append("\ndisappear and all the squares that were above drop down");
		sb.append("\nThe goal is to keep the game field as empty as possible");
		sb.append("\n\n\nUse \"Show Hints\" toggle button if you need help playing");
		sb.append("\nOr enter automated mode by pressing enter \"Auto\" mode button");
		sb.append("\n\n\n\nShortcuts:");
		sb.append("\na - enter/exit \"AUTO\" mode");
		sb.append("\ni - show/disable hint arrows");
		sb.append("\nh - show this menu");
		sb.append("\np - play/pause game");
		sb.append("\ns - start new game");
		sb.append("\n\n<- move current shape left");
		sb.append("\n-> move current shape right");
		sb.append("\n^ - rotate current shape clockwise");
		sb.append("\n\\/ - drop current shape to the bottom");
				
		JOptionPane.showMessageDialog(this, sb.toString(), "Help" , JOptionPane.INFORMATION_MESSAGE);
		mInterfaceEventListener.unPauseGame();
		
	}


	@Override
	// this function processes toggle button presses and state changes
	// for AI_MODE_BUTTON and SHOW_HINTS_BUTTON
	public void itemStateChanged(ItemEvent e) {
		if (mInterfaceEventListener != null)
		{
			JToggleButton source = (JToggleButton)e.getSource();
			
			if (source.getName().equals(TetrisContract.AI_MODE_BUTTON))
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					source.setText(TetrisContract.AI_MODE_BUTTON_LABEL_PRESSED);
					mInterfaceEventListener.onAIButtonPressed(true);
				}
				else
				{
					source.setText(TetrisContract.AI_MODE_BUTTON_LABEL_DEPRESSED);
					mInterfaceEventListener.onAIButtonPressed(false);
				}
			else if (source.getName().equals(TetrisContract.SHOW_HINTS_BUTTON))
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					source.setText(TetrisContract.SHOW_HINTS_BUTTON_LABEL_PRESSED);
					mInterfaceEventListener.onShowHintsButtonPressed(true);
				}
				else
				{
					source.setText(TetrisContract.SHOW_HINTS_BUTTON_LABEL_DEPRESSED);
					mInterfaceEventListener.onShowHintsButtonPressed(false);
				}
		}
	}
}
