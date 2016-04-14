package tetris_model;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import model.listeners.ModelListener;
import tetris_model.events.GameEvent;

public class Game
{
	private GameField mGameField;
	private ACTION_TYPE mAction;
	private List<List<Integer>> mNextShapeBoard;

	private final static int GAME_SPEED = 1000;
	private final static int BASE_WAIT_INTERVAL = 1000;
	private final static int SCORE_BASE = 100;
	private boolean mGamePlay;
	private boolean mGameStarted;
	private boolean mGameOver;
	Timer mTimer;

	private ModelListener mListener;

	private int mLevel;
	private int mScore;

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
		mGameField.startMove();
		fireStartGameEvent();
	}
	
	
	public void fireStartGameEvent()
	{
		GameEvent ev = new GameEvent(this, mGameStarted, mGameOver, mGamePlay);
		int i;
		ev.setBoard(mGameField.getBoard());
		ev.setNextShape(mNextShapeBoard);
		ev.setLevel(1);
		ev.setScore(0);
		ev.setCoverTransparency(0);
		if (mListener != null)
			i = 42;
		
	}
	
	public void fireGameOverEvent()
	{
		
	}
	

	public void setPlay(boolean state)
	{
		mGamePlay = state;
	}

	public void startGame()
	{
		mGameField.startMove();

		do
		{
			fireStartTurnEvent();

			do
			{
				fireSuccessfulMoveEvent();
				turnWait();

			} while (mGameField.moveShapeDown());

			fireLinesDeletedEvent(mGameField.deleteLines());

		} while (mGameField.nextMove());

		gameOver();
	}

	public void onLeftKeyAction()
	{
		if (mGameField.moveShapeLeft())
			fireUpdateInterface();
		else
			notifyError();

	}

	public void onRightKeyAction()
	{
		if (mGameField.moveShapeRight())
			fireUpdateInterface();
		else
			notifyError();
	}

	public void onUpKeyAction()
	{
		if (mGameField.rotate())
			fireUpdateInterface();
		else
			notifyError();
	}

	public void onDownKeyAction()
	{
		mGameField.drop();
		fireUpdateInterface();
		notifyFigureDropped();
	}

	private int calculateWaitTime()
	{
		return GAME_SPEED / mLevel;
	}

	private void fireUpdateInterface()
	{

	}

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

	public void fireSuccessfulMoveEvent()
	{
		if(mListener!=null)
		{
			String message = mGameField.boardToString();
			
			mListener.onMove(message);// reprint board
			
		}
	}

	public void fireDeleteLinesEvent()
	{
		// animate line deletion
	}

	public void setModelListener(ModelListener listener)
	{
		mListener = listener;
	}

	private void turnWait()
	{
		int timeFraction = 10;
		int waitTime = calculateWaitTime() / timeFraction;

		for (int i = 0; i < timeFraction; i++)
		{
			if (!mGamePlay)
				while (!mGamePlay)
				{
					try
					{
						Thread.sleep(100);
					}

					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			else
				try
				{
					Thread.sleep(waitTime);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	static enum ACTION_TYPE
	{
		ROTATE, LEFT, RIGHT, DOWN, DROP, LINE_DELETE;
	}

}
