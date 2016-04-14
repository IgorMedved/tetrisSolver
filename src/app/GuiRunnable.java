package app;

import tetris_ui.MainFrame;

public class GuiRunnable implements Runnable
{
	private MainFrame mFrame;
	
	public void run()
	{
		mFrame = new MainFrame();
	}
	
	public MainFrame getFrameHandle()
	{
		return mFrame;
	}
}
