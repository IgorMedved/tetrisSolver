package controller;

import tetriswindow.TetrisMainScreenButtonPressedListener;
import tetriswindow.UserInterface;
import tetriswindow.Window;

public class Controller implements TetrisMainScreenButtonPressedListener
{
	private Window mWindow;
	private UserInterface mInterface;
	
	public Controller()
	{
		mWindow = new Window();
		mInterface = mWindow.getPanel();
		mInterface.setListener(this);
		
	}
	
	public void run()
	{
		//mWindow = new 
	}

	@Override
	public void onPlayButtonPressed()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAIPlayButtonPressed()
	{
		// TODO Auto-generated method stub
		
	}
}
