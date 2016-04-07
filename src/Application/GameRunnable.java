package Application;

import tetriswindow.listeners.TetrisMainScreenButtonPressedListener;

public class GameRunnable implements Runnable
{
	TetrisMainScreenButtonPressedListener mListener;
	
	public void run()
	{
		System.out.println("mListner in Game Runnable is " + mListener);
		if (mListener != null)
			mListener.onPlayButtonPressed();
	}
	
	public synchronized void  setPlayButtonPressedListener (TetrisMainScreenButtonPressedListener listener)
	{
		mListener = listener;
		System.out.println("Set listener in Game runnable to " +mListener);
	}
	
}
