package app;

import tetris_ui.events.UI_EventListener;

public class GameRunnable implements Runnable
{
	UI_EventListener mListener;
	
	public void run()
	{
		System.out.println("mListner in Game Runnable is " + mListener);
		if (mListener != null)
			mListener.onPlayButtonPressed(null);
	}
	
	public synchronized void  setPlayButtonPressedListener (UI_EventListener listener)
	{
		mListener = listener;
		System.out.println("Set listener in Game runnable to " +mListener);
	}
	
}
