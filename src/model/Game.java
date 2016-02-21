package model;

import java.awt.Toolkit;
import java.util.List;

class Game
{
	GameField mGameField;
	
	private final static int GAME_SPEED = 100;
	private final static int SCORE_BASE = 100;
	
	private boolean mGamePlayOn;
	private int mLevel;
	private int mScore;
	

	public Game()
	{
		mGameField = new GameField();
		mLevel =1;
		mScore = 0;
	}
	
	public void startGame() 
	{
		mGameField.startMove();
		
		do
		{
			try
			{
				do
				{
					Thread.sleep(calculateWaitTime());
					fireUpdateInterface();
				}
				while (mGameField.moveShapeDown());
				System.out.println("Can't move down");
				fireLinesDeletedEvent(mGameField.deleteLines());
					
				 
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			mScore += deletedLines.size()*SCORE_BASE;
		// show animation for deleted lines
		fireUpdateInterface();
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

	
}
