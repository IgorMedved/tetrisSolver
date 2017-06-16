package tetris_ui.events;

import java.util.EventListener;

public interface UI_EventListener extends EventListener{

	public void onPlayButtonPressed(PlayButtonPressEvent e);
	public void onAIButtonPressed(boolean useAI);
	public void onShowHintsButtonPressed (boolean showHints);
	public void onHelpButtonPressed();
	public void onKeyPress (KeyBindingEvent e);
	public void pauseGame();
	public void unPauseGame();
	public void initializeNewGame();
	
	
	
}
