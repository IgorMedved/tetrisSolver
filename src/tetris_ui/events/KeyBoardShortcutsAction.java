package tetris_ui.events;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class KeyBoardShortcutsAction extends AbstractAction{
	public static final String PLAY_PAUSE_KEY = "P";
	public static final String RESTART_GAME_KEY = "S";
	public static final String SHOW_HINTS_KEY = "I";
	public static final String SHOW_HELP_KEY = "H";
	public static final String PLAY_AI_MODE_KEY = "A";
	
	
	public static final String ACTN_PLAY_PAUSE = "play_pause";
	public static final String ACTN_RESTART = "restart";
	public static final String ACTN_SHOW_HINTS = "hints";
	public static final String ACTN_SHOW_HELP = "help";
	public static final String ACTN_AI_MODE = "ai";
	
	protected UI_EventListener mListener;
	
	public KeyBoardShortcutsAction (String name, UI_EventListener uiEventListener)
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
