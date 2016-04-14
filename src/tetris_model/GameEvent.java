package tetris_model;

import java.util.EventObject;
import java.util.List;

public class GameEvent extends EventObject
{
	private List<List<Boolean>> mBoard;
	private int mLevel;
	
	public GameEvent (Object source)
	{
		super(source);
	}
	
	public GameEvent (Object source, List<List<Boolean>> board, int level, int score, boolean isGamePaused, boolean isGameOver)
	{
		super(source);
		mBoard= board;
		
		
	}
}
