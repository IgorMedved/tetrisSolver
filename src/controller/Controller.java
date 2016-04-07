package controller;

import model.Game;
import tetriswindow.listeners.KeyPressListener;
import tetriswindow.listeners.TetrisMainScreenButtonPressedListener;

public class Controller  implements TetrisMainScreenButtonPressedListener, KeyPressListener
{
	private Game mGame;
	private boolean mGamePlayOn;
	public static int playCounter = 0;
	
	public Controller()
	{
		mGamePlayOn = false; // initialize to false and wait for start button to be pressed
		
	}
	
	public void setModel(Game game)
	{
		mGame = game;
	}
	
	public void run()
	{
		  
	}

	@Override
	public synchronized void onPlayButtonPressed()
	{
		switchGameStatus();
		if (playCounter == 0)
		{
			mGame.startGame();
			
		}
		playCounter++;
		
	}

	@Override
	public void onAIPlayButtonPressed()
	{
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void onUpKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("up key pressed");
		if (mGamePlayOn)
			mGame.onUpKeyAction();
		
	}

	@Override
	public void onDownKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("Down key pressed");
		if (mGamePlayOn)
			mGame.onDownKeyAction();
	}

	@Override
	public void onLeftKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("Left key pressed");
		if (mGamePlayOn)
			mGame.onLeftKeyAction();
	}

	@Override
	public void onRightKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("Right key pressed");
		if (mGamePlayOn)
			mGame.onRightKeyAction();
	}

	@Override
	public void switchGameStatus()
	{
		mGamePlayOn = mGamePlayOn? false:true; // reverse mGamePlayOn
		mGame.setPlay(mGamePlayOn);
		
	}
}
