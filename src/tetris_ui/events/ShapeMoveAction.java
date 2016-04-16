package tetris_ui.events;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


public class ShapeMoveAction extends AbstractAction
{
	public static final String ROTATE_KEY = "UP";
	public static final String DROP_KEY = "DOWN";
	public static final String LEFT_KEY = "LEFT";
	public static final String RIGHT_KEY = "RIGHT";
	
	
	public static final String ACTN_ROTATE = "rotate";
	public static final String ACTN_DROP = "drop";
	public static final String ACTN_LEFT = "left";
	public static final String ACTN_RIGHT = "right";
	
	
	
	protected UI_EventListener mListener;
	
	public ShapeMoveAction (String name, UI_EventListener uiEventListener)
	{
		super(name);
		
		mListener = uiEventListener;
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (mListener != null)
		{
		
			KeyBindingEvent ev = new KeyBindingEvent (e.getSource(), (String)getValue(NAME));
			mListener.onKeyPress(ev);
		}
		
		
	}
		
	

}
