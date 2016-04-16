package tetris_model.events;

import java.util.EventObject;
import java.util.List;

public class GameEvent extends EventObject
{
	public static final int NOT_UPDATED = -1;
	
	List<List<Integer>> mBoard;
	List<List<Integer>> mNextShape;
	List<Integer> mLinesDeleted;
	int mScore;
	int mLevel;
	boolean mStartGame;
	boolean mGameOver;
	boolean mGamePlayed;
	int mCoverOpacity; // integer from 0 to 255;
	
	
	
	
	public GameEvent(Object source, boolean isStartGame, boolean isGameOver, boolean isGamePlayed)
	{
		super(source);
		
		mBoard = null;
		mNextShape = null;
		mLinesDeleted = null;
		mScore = NOT_UPDATED;
		mLevel = NOT_UPDATED;
		mStartGame = isStartGame;
		mGameOver = isGameOver;
		mGamePlayed = isGamePlayed;
		mCoverOpacity = NOT_UPDATED;
	}




	public  List<List<Integer>> getBoard()
	{
		return mBoard;
	}




	public List<List<Integer>> getNextShape()
	{
		return mNextShape;
	}




	public List<Integer> getLinesDeleted()
	{
		return mLinesDeleted;
	}




	public int getScore()
	{
		return mScore;
	}




	public int getLevel()
	{
		return mLevel;
	}




	public boolean isStartGame()
	{
		return mStartGame;
	}




	public boolean isGameOver()
	{
		return mGameOver;
	}




	public boolean isGamePlayed()
	{
		return mGamePlayed;
	}




	public int getCoverOpacity()
	{
		return mCoverOpacity;
	}




	public void setBoard(List<List<Integer>> board)
	{
		mBoard = board;
	}




	public void setNextShape(List<List<Integer>> nextShape)
	{
		mNextShape = nextShape;
	}




	public void setLinesDeleted(List<Integer> linesDeleted)
	{
		mLinesDeleted = linesDeleted;
	}




	public void setScore(int score)
	{
		mScore = score;
	}




	public void setLevel(int level)
	{
		mLevel = level;
	}




	public void setStartGame(boolean startGame)
	{
		mStartGame = startGame;
	}




	public void setGameOver(boolean gameOver)
	{
		mGameOver = gameOver;
	}




	public void setGamePlayed(boolean gamePlayed)
	{
		mGamePlayed = gamePlayed;
	}




	public void setCoverTransparency(int coverTransparency)
	{
		mCoverOpacity = coverTransparency;
	}
	
	
}
