package model;

import java.awt.Toolkit;
import java.util.List;

import model.listeners.ModelListener;

public class Game
{
	private GameField mGameField;
	private ACTION_TYPE mAction;
	
	private final static int GAME_SPEED = 1000;
	private final static int SCORE_BASE = 100;
	private boolean isGamePlay;
	
	private ModelListener mListener;
	
	
	private int mLevel;
	private int mScore;
	

	public Game()
	{
		mGameField = new GameField();
		mLevel =1;
		mScore = 0;
	}
	
	public void setPlay (boolean state)
	{
		isGamePlay = state;
	}
	
	public void startGame() 
	{
		mGameField.startMove();
		
		
		do
		{
			fireStartTurnEvent();
			
				do
				{
					fireSuccessfullMoveEvent();
					turnWait();
					
				}
				while (mGameField.moveShapeDown());
				
				fireLinesDeletedEvent(mGameField.deleteLines());
					
				
			
		}
		while (mGameField.nextMove());
		
		gameOver();
	}
	
	public void onLeftKeyAction ()
	{
		if (mGameField.moveShapeLeft())
		  	fireUpdateInterface();
		else
			notifyError();
		
	}
	
	public void onRightKeyAction ()
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
		return GAME_SPEED/mLevel;
	}
	
	private void fireUpdateInterface()
	{
		
	}
	
	private void fireLinesDeletedEvent(List <Integer> deletedLines)
	{
		
		if (deletedLines != null)
		{
			mScore += deletedLines.size()*SCORE_BASE;
			// show animation for deleted lines
			fireSuccessfullMoveEvent();
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
	
	public void fireSuccessfullMoveEvent()
	{
		//if(mListener!=null)
		{
			String message = mGameField.boardToString();
			//System.out.println("mlistener is not null");
			mListener.onMove(message);// reprint board
			//System.out.println(message);
		}
	}
	
	public void fireDeleteLinesEvent()
	{
		// animate line deletion
	}
	
	public void setModelListener (ModelListener listener)
	{
		mListener = listener;
	}
	private void turnWait ()
	{
		int timeFraction = 10;
		int waitTime = calculateWaitTime()/timeFraction;
		
		for (int i = 0; i<timeFraction; i++)
		{
			if (!isGamePlay)
				try
				{
					wait();
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	

	static enum ACTION_TYPE	{
		ROTATE,
		LEFT,
		RIGHT,
		DOWN,
		DROP,
		LINE_DELETE;
	}
	
}
