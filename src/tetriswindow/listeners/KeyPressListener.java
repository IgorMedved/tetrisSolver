package tetriswindow.listeners;

public interface KeyPressListener extends TetrisListener
{
	public void onUpKeyPressed ();
	public void onDownKeyPressed();
	public void onLeftKeyPressed();
	public void onRightKeyPressed();
	
	// public void onSpaceKeyPressed();
	
}
