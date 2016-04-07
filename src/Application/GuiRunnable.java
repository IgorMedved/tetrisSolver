package Application;

import tetriswindow.MainFrame;

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
