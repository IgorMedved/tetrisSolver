package tetriswindow.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import tetriswindow.listeners.KeyPressListener;

public class MoveAction extends AbstractAction
{
	public static final String ROTATE_KEY = "UP";
	public static final String DROP_KEY = "DOWN";
	public static final String LEFT_KEY = "LEFT";
	public static final String RIGHT_KEY = "RIGHT";
	
	
	public static final String ACTN_ROTATE = "rotate";
	public static final String ACTN_DROP = "drop";
	public static final String ACTN_LEFT = "left";
	public static final String ACTN_RIGHT = "right";
	
	
	
	protected KeyPressListener mListener;
	
	public MoveAction (String name, KeyPressListener listener)
	{
		super(name);
		
		mListener = listener;
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if (mListener==null)
			return;
		
		
		String actionName = (String)this.getValue(this.NAME);
		if (actionName.equals(ACTN_ROTATE))
			mListener.onUpKeyPressed();
		else if (actionName.equals(ACTN_DROP))
			mListener.onDownKeyPressed();
		else if (actionName.equals(ACTN_LEFT))
			mListener.onLeftKeyPressed();
		else if (actionName.equals(ACTN_RIGHT))
			mListener.onRightKeyPressed();
	}
		
	

}
