package tetris_ui.events;

import java.util.EventListener;

public interface UI_EventListener extends EventListener{

	public void onPlayButtonPressed(PlayButtonPressEvent e);
	public void onAIButtonPressed(AIButtonPressEvent e);
	public void onKeyPress (KeyBindingEvent e);
	
	
}
