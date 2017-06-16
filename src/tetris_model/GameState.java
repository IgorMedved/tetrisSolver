package tetris_model;

//This class has all the relevant curent game statistics and state
// such as game score, level, information on whether the game is played or paused, etc
public class GameState {

	private boolean mGamePlay;// is game paused or not
	private boolean mGameStarted; // has the game started
	private boolean mGameOver; // is the game over or not
	private boolean mGameWon; // when the game is over is it won or lost
	private boolean mUseAi; // user preference normal mode or auto mode
	private boolean mShowHelp; // user preference show hints or not
	
	private boolean isStateUpdated; // is there a need to update score, level, etc
	private boolean isGamePlayStatusUpdated; // whether one of the variables mGamePlay, mGameStarted, mGameOver or mGameWon was updated
	private int mLevel; // current level
	private int mScore; // current score
	private int mPictureOpacity; // opacity of the cover that hides the secret picture
	private int mLinesDeleted; // number of lines that were deleted during last move
	private int mWaitTime; // current game speed
	
	public GameState(boolean useAi, boolean showHelp)
	{
		mGamePlay = false;
		mGameStarted = false;
		mGameOver = false;
		mGameWon = false;
		mWaitTime = GameDefinitions.BASE_WAIT_INTERVAL;
		mUseAi = useAi;
		mShowHelp = showHelp;
	}
	
	// default constructor start game in normal mode and do not show hints
	public GameState()
	{
		this(false, false);
	}
	
	
	public void startGame ()
	{
		mGamePlay = true;
		mGameStarted = true;
		mGameOver = false;
		mGameWon = false;
		mPictureOpacity = 255;
		mLevel = 1;
		mScore = 0;
		mLinesDeleted = 0;
		mWaitTime = GameDefinitions.BASE_WAIT_INTERVAL;
		isStateUpdated = true;
		isGamePlayStatusUpdated = true;
	}
	
	public synchronized void startTurn()
	{
		if (mLinesDeleted != 0)
		{
			isStateUpdated = true;
			mScore += GameDefinitions.SCORE_BASE * mLinesDeleted;
			//ev.setScore(mScore);
			evaluateOpacity();
			int oldLevel = mLevel; // 
			evaluateLevel();
			if (oldLevel != mLevel)
			{
				mGamePlay = false; // the game is paused when the it goes to new level
				isGamePlayStatusUpdated = true;
			}
			evaluateWaitTime();
		}
		else
		{
			isStateUpdated = false;
			isGamePlayStatusUpdated = false;
		}
		if (mLevel == 10)
		{
			gameWon();
		}
	}
	
	private void gameWon()
	{
		gameOver();
		mGameWon = true;
	}
	
	public void gameOver()
	{
		mGamePlay = false;
		mGameOver = true;
		mGameStarted = false;
		isStateUpdated = true;
		isGamePlayStatusUpdated = true;
	}
	
	public void pauseGame()
	{
		mGamePlay = false;
		isStateUpdated = false;
		isGamePlayStatusUpdated = true;
	}
	

	public void restartGame()
	{
		mGamePlay = true;
		isGamePlayStatusUpdated = true;
		
		// every time the game has reached a new level it pauses during the pause the cover of the secret picture 
		// is completely transparent, however at the start of the new turn the transparency needs to be upgraded
		// to reflect current score and level
		int oldOpacity = mPictureOpacity; 
		evaluateOpacity();
		if (oldOpacity != mPictureOpacity)
			isStateUpdated = true;
	}
	
	// evaluate transparency of the cover hiding a secret picture based on current level and score
	private void evaluateOpacity()
	{
		
		if (mScore > GameDefinitions.LEVELSCORES[GameDefinitions.LEVELSCORES.length - 1])
			mPictureOpacity = 0;
		else if (mScore < GameDefinitions.LEVELSCORES[0] || mLevel < 2)
		{
			mPictureOpacity = 255 * (GameDefinitions.LEVELSCORES[0] - mScore) / (GameDefinitions.LEVELSCORES[0]);
			mPictureOpacity = mPictureOpacity < 0 ? 0 : mPictureOpacity;
		} else
		{
			mPictureOpacity = 255 * (GameDefinitions.LEVELSCORES[mLevel - 1] - mScore)
				/ (GameDefinitions.LEVELSCORES[mLevel - 1] - GameDefinitions.LEVELSCORES[mLevel - 2]);
			mPictureOpacity = mPictureOpacity < 0 ? 0 : mPictureOpacity;
		}
	}
	
	// evaluate level based on current score
	private void evaluateLevel()
	{
		if (mLevel <= GameDefinitions.LEVELSCORES.length && mScore >= GameDefinitions.LEVELSCORES[mLevel-1])
		   mLevel ++;
	}
	
	
	public boolean isGamePlay() {
		return mGamePlay;
	}

	
	public boolean isGameStarted() {
		return mGameStarted;
	}

	
	public boolean isGameOver() {
		return mGameOver;
	}


	public boolean isStateUpdated() {
		return isStateUpdated;
	}

	
	public int getLevel() {
		return mLevel;
	}


	public int getScore() {
		return mScore;
	}


	public int getPictureOpacity() {
		return mPictureOpacity;
	}

	
	public int getLinesDeleted() {
		return mLinesDeleted;
	}


	public void setLinesDeleted(int linesDeleted) {
		mLinesDeleted = linesDeleted;
	}

	public boolean isGamePlayStatusUpdated() {
		return isGamePlayStatusUpdated;
	}

	public void setGamePlayStatusUpdated(boolean isGamePlayStatusUpdated) {
		this.isGamePlayStatusUpdated = isGamePlayStatusUpdated;
	}


	public void setStateUpdated(boolean isStateUpdated) {
		this.isStateUpdated = isStateUpdated;
	}
	
	
	public int getWaitTime() {
		return mWaitTime;
	}


	private void evaluateWaitTime()
	{
		mWaitTime=  GameDefinitions.BASE_WAIT_INTERVAL / mLevel;
	}

	

	public boolean isGameWon() {
		return mGameWon;
	}

	public boolean isUseAi() {
		return mUseAi;
	}

	public void setUseAi(boolean useAi) {
		mUseAi = useAi;
	}

	public boolean isShowHelp() {
		return mShowHelp;
	}

	public void setShowHelp(boolean showHelp) {
		mShowHelp = showHelp;
	}

// class containing some constants used in calculating game level, game speed and game score
	public static class GameDefinitions {
		// scores required to reach next level
		public final static int[] LEVELSCORES = new int[] { 100, 300, 600, 1000, 1500, 2100, 2800, 3600, 4500};
		private final static int BASE_WAIT_INTERVAL = 1000; // the wait between to consecutive moves of the shape down in ms
		private final static int SCORE_BASE = 10; // The score increases by 10 points for every line deleted
	}
}
