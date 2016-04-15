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
	//private ACTION_TYPE mAction;
	private List<List<Integer>> mNextShapeBoard;
	
	public final static int LEVEL_2 = 100;
	public final static int LEVEL_3 = 300;
	public final static int LEVEL_4 = 600;
	public final static int LEVEL_5 = 1000;

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
	
	private int mPictureTransparency;

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
				fireLinesDeletedEvent(mGameField.deleteLines());
				if (mGameField.nextMove())
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
		mPictureTransparency = getTransparency();
//		mOldScore = mScore;
//		mOldLevel = mLevel;
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
	
	public void fireLinesDeleted(List <Integer> deletedLines)
	{
		if (mListener != null)
		{
			GameEvent ev = new GameEvent(this, mGameStarted, mGameOver, mGamePlay);
			ev.setBoard(mGameField.getBoard());
			if (deletedLines.size() != 0)
			{
				mScore += SCORE_BASE*deletedLines.size();
				ev.setScore(mScore);
				int newLevel = getLevel ();
				if (newLevel > mLevel)
				{
					mLevel = newLevel;
					ev.setLevel(mLevel);
					
				}
				int newTransparency = getTransparency();
				if (newTransparency != mPictureTransparency)
				{
					mPictureTransparency = newTransparency;
					ev.setCoverTransparency(mPictureTransparency);
				}
					
				
			}
		}
	}
	
	public int getLevel()
	{
		if (mScore < 100)
			return 1;
		else
			return 2;
	}
	
	public int getTransparency ()
	
	{
		
	}
	
	public void fireGameOverEvent()
	{
		
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

/*	private void fireUpdateInterface()
	{

	}*/

	private void fireLinesDeletedEvent(List<Integer> deletedLines)
	{

		if (deletedLines != null)
		{
			mScore += deletedLines.size() * SCORE_BASE;
			// show animation for deleted lines
			fireSuccessfulMoveEvent();
		}

	}

	private void notifyError()
	{
		Toolkit.getDefaultToolkit().beep();
	}

	private void notifyFigureDropped()
	{
		Toolkit.getDefaultToolkit().beep();
	}

	private void gameOver()
	{
		System.out.println("Game Over!");
	}

	public void fireStartTurnEvent()
	{
		// update score if needed
		// update nextShape
	}

/*	public void fireSuccessfulMoveEvent()
	{
		if(mListener!=null)
		{
			String message = mGameField.boardToString();
			
			mListener.onMove(message);// reprint board
			
		}
	}*/

	public void fireDeleteLinesEvent()
	{
		// animate line deletion
	}

	public void setModelListener(ModelListener listener)
	{
		mListener = listener;
	}


/*
	static enum ACTION_TYPE
	{
		ROTATE, LEFT, RIGHT, DOWN, DROP, LINE_DELETE;
	}*/

}
