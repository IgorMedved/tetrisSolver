package tetris_model.events;

import java.util.EventListener;

public interface GameEventListener extends EventListener
{
	public void gameEventOccured (GameEvent e);
}
