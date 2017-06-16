package tetris_model.events;

import java.util.Collections;
import java.util.EventObject;
import java.util.List;

import tetris_model.Board;
import tetris_model.BoardType;
import tetris_model.GameState;
import tetris_model.Point;
// the object used to pass information from model to GUI in order to update GUI after model changes
public class GameEvent extends EventObject
{
	public static final int NOT_UPDATED = -1;
	
	private List<List<Integer>> mBoard; // representation of the game board
	private static List<List<Integer>> mNextShapeBoard = new Board (BoardType.NEXTSHAPEBOARD).getBoard(); // representation of the 
	// next shape board it is always the same
	private List<Point> mCurrentShape;
	private List<Point> mNextShape;
	private List<Point> mHintsLocations;
	private int mCurrentShapeType;
	private int mNextShapeType;
	private List<Integer> mLinesDeleted;
	private int mScore;
	private int mLevel;
	private boolean mStartGame;
	private boolean mGameOver;
	private boolean mGamePlayed;
	private boolean mGameWon;
	private int mCoverOpacity; // integer from 0 to 255;
	private boolean mGameStartedUpdated;
	private boolean mGamePlayedUpdated;
	private boolean mGameOverUpdated;
	private String message;
	
	public GameEvent(Object source, GameState state, List<List<Integer>> mainBoard, List<Point> currentShape, int currentShapeType, List<Point> nextShape, int nextShapeType, String message)
	{
		super(source);
		
			mBoard = mainBoard;
			mCurrentShape = currentShape;
			mCurrentShapeType = currentShapeType;
			mNextShape = nextShape;
			
			mNextShapeType = nextShapeType;
		
		
		mLinesDeleted = null;
		if (state.isStateUpdated())
		{
			mScore = state.getScore();
			mLevel = state.getLevel();
			mCoverOpacity = state.getPictureOpacity();
		}
		else
		{
			mScore = NOT_UPDATED;
			mLevel = NOT_UPDATED;
			mCoverOpacity = NOT_UPDATED;
		}
		mStartGame = state.isGameStarted();
		mGameOver = state.isGameOver();
		mGamePlayed = state.isGamePlay();
		mGameWon = state.isGameWon();
		
		if (state.isGamePlayStatusUpdated())
		{
			mGameStartedUpdated = true;
			mGameOverUpdated = true;
			mGamePlayedUpdated = true;
			
		}
		else
		{
			mGameStartedUpdated = false;
			mGameOverUpdated = false;
			mGamePlayedUpdated = false;
			
		}
		
//		System.out.println("The event came from " + message);
		this.message = message;
	}


	public  List<List<Integer>> getBoard()
	{
		return mBoard== null? null: Collections.unmodifiableList(mBoard);
	}


	public List<List<Integer>> getNextShapeBoard()
	{
		return mNextShapeBoard == null? null: Collections.unmodifiableList(mNextShapeBoard);
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


	public List<Point> getCurrentShape() {
		return mCurrentShape;
	}


	public void setCurrentShape(List<Point> currentShape) {
		mCurrentShape = currentShape;
	}


	public List<Point> getNextShape() {
		return mNextShape;
	}


	public void setNextShape(List<Point> nextShape) {
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
	

	public boolean isGameStartedUpdated() {
		return mGameStartedUpdated;
	}


	public void setGameStartedUpdated(boolean mGameStartedUpdated) {
		this.mGameStartedUpdated = mGameStartedUpdated;
	}


	public boolean isGamePlayedUpdated() {
		return mGamePlayedUpdated;
	}


	public void setGamePlayedUpdated(boolean mGamePlayedUpdated) {
		this.mGamePlayedUpdated = mGamePlayedUpdated;
	}


	public boolean isGameOverUpdated() {
		return mGameOverUpdated;
	}


	public void setGameOverUpdated(boolean mGameOverUpdated) {
		this.mGameOverUpdated = mGameOverUpdated;
	}


	public List<Point> getHintsLocations() {
		return mHintsLocations;
	}


	public void setHintsLocations(List<Point> hintsLocations) {
		mHintsLocations = hintsLocations;
	}
	

	public static List<List<Integer>> getmNextShapeBoard() {
		return mNextShapeBoard;
	}


	public int getCurrentShapeType() {
		return mCurrentShapeType;
	}

	
	public int getNextShapeType() {
		return mNextShapeType;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public boolean isGameWon() {
		return mGameWon;
	}
	
	
	
	
}
