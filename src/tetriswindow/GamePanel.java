package tetriswindow;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import model.listeners.ModelListener;
import tetriswindow.actions.MoveAction;
import tetriswindow.listeners.KeyPressListener;
import tetriswindow.listeners.TetrisListener;
import tetriswindow.listeners.TetrisMainScreenButtonPressedListener;
import Application.GameRunnable;



public class GamePanel extends JPanel implements ActionListener, ModelListener
{
	private static final String PLAY = "|> Play";
	private static final String PAUSE  = "|| Pause";
	
	private static final String PAUSE_MESSAGE = "Game Paused";
	private static final String GAME_OVER = "GAME OVER!";
	private static final String START_GAME = "Press play button to start";
	
	private static final String AI = "AI Play";
	private static final String HUMAN_PLAY = "Human play";
	
	private JButton mBtnPlayPause;
	private JButton mBtnAIMode;
	

	private JLabel mLblGameField;
	private JLabel mLblgameOverPause;
	private JLabel mLblNextFigure;
	private JLabel mLblScore;
	

	TetrisMainScreenButtonPressedListener mButtonListener;
	KeyPressListener mKeyPressListener;
	
	private GameRunnable mGame;
	private Thread mGameThread;

	public GamePanel()
	{
		mBtnPlayPause = new JButton(PLAY);
		mBtnAIMode = new JButton(AI);
		
		mLblGameField = new JLabel();
		mLblgameOverPause = new JLabel(START_GAME);
		mLblNextFigure = new JLabel();

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
		gc.gridy = 10;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(50, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;

		add(mBtnPlayPause, gc);
		
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 2;
		gc.gridy = 10;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(50, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;

		add(mBtnAIMode, gc);

		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.weightx = 2;
		gc.weighty = 10;
		gc.insets = new Insets(50, 10, 0, 0);

		gc.fill = GridBagConstraints.NONE;
		add(mLblGameField, gc);

		setGameFieldLabel("here");
		
		//setScoreLabel("here");
		
		mBtnPlayPause.addActionListener(this);
		mBtnAIMode.addActionListener(this);
		
/*		
		btnGenerateReportFiles.addActionListener(this);
		btnSelectDir.addActionListener(this);
		btnValidateFiles.addActionListener(this);
		btnOpenLogFiles.addActionListener(this);

		btnGenerateReportFiles.setEnabled(false);
		btnValidateFiles.setEnabled(false);
		btnOpenLogFiles.setEnabled(false);*/

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == mBtnPlayPause)
		{	
			firePlayPauseEvent();
			
		}
		else if (e.getSource() == mBtnAIMode)
		{
			fireAIOnOffEvent();
		}
		
			
		

	}
	
	public void setGameFieldLabel(String message)
	{
		mLblGameField.setText(message);
	}
	
	
	
	public void setScoreLabel(String message)
	{
		
		
		mLblScore.setText(message);
	}
	
	private void firePlayPauseEvent()
	
	{
		/// handle to thread responsible for running a game is needed
		if (mGame == null)
		{
			mGame = new GameRunnable();
			mGameThread = new Thread(mGame);
			mGame.setPlayButtonPressedListener(mButtonListener);
			mGameThread.start();
		}
		else
			mButtonListener.switchGameStatus();
			
		
		if (mButtonListener != null)
			mGame.setPlayButtonPressedListener(mButtonListener);
			
		
		if (mGameThread == null)
			mGameThread = new Thread(mGame);
		
		
	}
	
	private void fireAIOnOffEvent()
	{
		
	}
	
	private void setButtonPressListener(TetrisListener listener)
	{
		mButtonListener = (TetrisMainScreenButtonPressedListener) listener;
	}
	
	private void setKeyPressListener (TetrisListener listener)
	{
		mKeyPressListener = (KeyPressListener) listener;
	}
	
	private void registerKeyActions()
	{
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(MoveAction.ROTATE_KEY), MoveAction.ACTN_ROTATE);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(MoveAction.LEFT_KEY), MoveAction.ACTN_LEFT);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(MoveAction.RIGHT_KEY), MoveAction.ACTN_RIGHT);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(MoveAction.DROP_KEY), MoveAction.ACTN_DROP);
		
		this.getActionMap().put(MoveAction.ACTN_ROTATE, new MoveAction(MoveAction.ACTN_ROTATE,mKeyPressListener));
		this.getActionMap().put(MoveAction.ACTN_LEFT, new MoveAction(MoveAction.ACTN_LEFT,mKeyPressListener));
		this.getActionMap().put(MoveAction.ACTN_RIGHT, new MoveAction(MoveAction.ACTN_RIGHT,mKeyPressListener)); 
		this.getActionMap().put(MoveAction.ACTN_DROP, new MoveAction(MoveAction.ACTN_DROP,mKeyPressListener));
	}
	
	public void setListeners (TetrisListener mListener)
	{
		setButtonPressListener(mListener);
		setKeyPressListener(mListener);
		registerKeyActions();
	}

	@Override
	public void onStartTurn(int nextShapeType, boolean shouldUpdateType, int newScore, boolean shouldUpdateScore)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMove(String message)
	{
		
		setGameFieldLabel(message);
		
	}

	@Override
	public void onLineDelete()
	{
		// TODO Auto-generated method stub
		
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(600, 800);
	}
	
	
/*
	

	
	private void fireDirectorySelectButtonPressedEvent()
	{
		if (mListener != null)
			mListener.onSelectButtonPressed();

	}

	private void fireValidateButtonPressedEvent()
	{
		if (mListener != null)
			mListener.onValidateButtonPressed();
	}

	

	

	

	public void setLblPath(String label)
	{
		lblPath.setText("Selected path: " + label);
	}

	public void setLblGenerate(String message)
	{
		lblGenerate.setText(message);
	}

	public void SetLblValidate(String message)
	{
		lblValidate.setText(message);
	}*/
}

