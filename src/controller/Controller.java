package controller;

import java.awt.event.ActionEvent;

import tetriswindow.UserInterface;
import tetriswindow.Window;
import tetriswindow.actions.MoveAction;
import tetriswindow.listeners.KeyPressListener;
import tetriswindow.listeners.TetrisMainScreenButtonPressedListener;

public class Controller  implements TetrisMainScreenButtonPressedListener, KeyPressListener
{
	private Window mWindow;
	private UserInterface mInterface;
	
	public Controller()
	{
		mWindow = new Window();
		mInterface = mWindow.getPanel();
		mInterface.setListeners(this);
		
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

	

	@Override
	public void onUpKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("up key pressed");
		
	}

	@Override
	public void onDownKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("Down key pressed");
	}

	@Override
	public void onLeftKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("Left key pressed");
	}

	@Override
	public void onRightKeyPressed()
	{
		// TODO Auto-generated method stub
		System.out.println("Right key pressed");
	}
}
