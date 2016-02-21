package tetriswindow.actions;

import java.awt.event.ActionEvent;

import tetriswindow.listeners.KeyPressListener;


public class RightAction extends MoveAction
{

	

	public RightAction(String name, KeyPressListener listener)
	{
		super(name, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (mListener!=null)
			mListener.onRightKeyPressed();
		
	}

}
