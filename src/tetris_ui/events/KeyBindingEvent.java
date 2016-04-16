package tetris_ui.events;

import java.util.EventObject;

public class KeyBindingEvent extends EventObject {
	
	private String mActionType;
	
	public KeyBindingEvent (Object source, String actionType)
	{
		super (source);
		mActionType = actionType;
	}
	
	public String getActionType()
	{
		return mActionType;
	}

}
