package tetris_model;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import model.listeners.ModelListener;
import tetris_model.events.GameEvent;
import tetris_model.events.GameEventListener;

public class Game
{
	private GameField mGameField;
	private List<List<Integer>> mNextShapeBoard;
	
	public final static int[] LEVELSCORES = new int[]{100,300,600,1000};
	


	private final static int GAME_SPEED = 1000;
	private final static int BASE_WAIT_INTERVAL = 1000;
	private final static int SCORE_BASE = 10;
	private boolean mGamePlay;
	private boolean mGameStarted;
	private boolean mGameOver;
	private Timer mTimer;

	private GameEventListener mListener;

	private int mLevel;
	private int mScore;
	
	private int mPictureOpacity;

	public Game()
	{
		mGamePlay = false;
		mGameStarted = false;
		mGameOver = false;
		mTimer = new Timer (BASE_WAIT_INTERVAL, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				move();
				
			}
			
		});
		
		
	}
	
	private void move()
	{
		if (mGameField != null)
		{
			if (mGameField.moveShapeDown())
				fireSuccessfulMoveEvent();
			else
			{
				mGameField.nextMove();
				fireLinesDeletedEvent(mGameField.deleteLines());
				if (mGameField.startMove())
					fireStartTurnEvent();
				else
					fireGameOverEvent();
			}
					
		}
	}
	
	private void initializeNewGame()
	{
		mGamePlay = true;
		mGameStarted = true;
		mGameOver = false;
		mGameField = new GameField();
		mNextShapeBoard = Board.generateNextShapeBoard();
		Board.insertShapeIntoNextBoard(mNextShapeBoard, mGameField.getNextShape());
		mLevel = 1;
		mScore = 0;
		mPictureOpacity = getOpacity();
		mGameField.startMove();
		fireStartGameEvent();
		
		
	}
	
	
	public void fireStartGameEvent()
	{
		if (mListener != null)
		{
			GameEvent ev = new GameEvent(this, mGameStarted, mGameOver, mGamePlay);
			ev.setBoard(mGameField.getBoard());
			ev.setNextShape(mNextShapeBoard);
			ev.setLevel(1);
			ev.setScore(0);
			ev.setCoverTransparency(255);
		
			mListener.gameEventOccurred(ev);
		}
		mTimer.start();
		
	}
	
	public void fireSuccessfulMoveEvent()
	{
		if (mListener != null)
		{
			GameEvent ev = new GameEvent (this, mGameStarted, mGameOver, mGamePlay);
			ev.setBoard(mGameField.getBoard());
			
			mListener.gameEventOccurred(ev);
			
		}
	}
	
	public void fireLinesDeletedEvent(List <Integer> deletedLines)
	{
		if (mListener != null)
		{
			GameEvent ev = new GameEvent(this, mGameStarted, mGameOver, mGamePlay);
			ev.setBoard(mGameField.getBoard());
			if (deletedLines.size() != 0)
			{
				mScore += SCORE_BASE*deletedLines.size();
				ev.setScore(mScore);
				int newTransparency = getOpacity();
				if (newTransparency != mPictureOpacity)
				{
					mPictureOpacity = newTransparency;
					ev.setCoverTransparency(mPictureOpacity);
				}
				int newLevel = getLevel ();
				if (newLevel > mLevel)
				{
					mLevel = newLevel;
					ev.setLevel(mLevel);
					
					mGamePlay = false;// pause a game when new level is reached
					ev.setGamePlayed(false);
					
					mTimer.stop();
				}
			}
			mNextShapeBoard =Board.generateNextShapeBoard();
			Board.insertShapeIntoNextBoard(mNextShapeBoard, mGameField.getNextShape());
			ev.setNextShape(mNextShapeBoard);
			
			
			mListener.gameEventOccurred(ev);
		}
	}
	
	public void fireStartTurnEvent()
	{
		if (mListener != null)
		{
			GameEvent ev = new GameEvent(this, mGameOver, mGameStarted, mGamePlay);
			ev.setBoard(mGameField.getBoard());
			int newOpacity = getOpacity();
			if (mPictureOpacity != newOpacity) // update opacity at start of the turn for the case when level changed
			{
				mPictureOpacity = newOpacity;
				ev.setCoverTransparency(mPictureOpacity);
			}
		}
	}

	public int getLevel()
	{
		for (int i = 0; i < LEVELSCORES.length; i++)
			if (mScore < LEVELSCORES[i])
				return i+1;
		return LEVELSCORES.length +1;
	}
	
	public int getOpacity ()
	
	{
		int opacity = -1;
		if(mScore > LEVELSCORES[LEVELSCORES.length-1])
			return 0;
		else if  (mScore <LEVELSCORES[0])
		{
			opacity = 255*(LEVELSCORES[0] -mScore)/(LEVELSCORES[0]);
			return opacity <0? 0: opacity;
		}
		else
		{
			opacity =255 * (LEVELSCORES[mLevel-1] -mScore)/(LEVELSCORES[mLevel-1] -LEVELSCORES[mLevel-2]);
			return opacity <0? 0: opacity;
		}
	}
			
	
	
	public void fireGameOverEvent()
	{
		mGamePlay = false;
		mGameOver = true;
		mGameStarted = false;
		
		GameEvent ev = new GameEvent (this, mGameStarted, mGameOver, mGamePlay);
		
		mListener.gameEventOccurred(ev);
		
	}
	

	public void setPlay(boolean state)
	{
		mGamePlay = state;
	}



	public void onLeftKeyAction()
	{
		if (mGameField.moveShapeLeft())
			fireSuccessfulMoveEvent();
		else
			notifyError();

	}

	public void onRightKeyAction()
	{
		if (mGameField.moveShapeRight())
			fireSuccessfulMoveEvent();
		else
			notifyError();
	}

	public void onUpKeyAction()
	{
		if (mGameField.rotate())
			fireSuccessfulMoveEvent();
		else
			notifyError();
	}

// this might change	
	public void onDownKeyAction()
	{
		mGameField.drop();
		fireSuccessfulMoveEvent();
		notifyFigureDropped();
	}

	private int calculateWaitTime()
	{
		return GAME_SPEED / mLevel;
	}



	private void notifyError()
	{
		Toolkit.getDefaultToolkit().beep();
	}

	private void notifyFigureDropped()
	{
		Toolkit.getDefaultToolkit().beep();
	}



	public void fireDeleteLinesEvent()
	{
		// animate line deletion
	}

	public void setModelListener(GameEventListener listener)
	{
		mListener = listener;
	}

}
