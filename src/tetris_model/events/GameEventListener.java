package tetris_model.events;

import java.util.EventListener;

//interface for passing control from controller to GUI to show model changes
public interface GameEventListener extends EventListener
{
	public void gameEventOccurred (GameEvent e);
}
