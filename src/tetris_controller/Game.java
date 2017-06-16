package tetris_controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.Timer;

import ai.AiGameField;
import ai.BoardHelper;
import ai.Evaluator;
import tetris_model.Board;
import tetris_model.GameField;
import tetris_model.GameState;
import tetris_model.Point;
import tetris_model.events.GameEvent;
import tetris_model.events.GameEventListener;
import tetris_ui.events.AIButtonPressEvent;
import tetris_ui.events.KeyBindingEvent;
import tetris_ui.events.KeyBoardShortcutsAction;
import tetris_ui.events.PlayButtonPressEvent;
import tetris_ui.events.ShapeMoveAction;
import tetris_ui.events.UI_EventListener;

// This is the main controller class that connects GUI to the model. It changes the model in response to 
// keyboard and interface button clicks and updates the user interface
// in response to Model changes
// It also contains a timer that triggers the shape dropping down as time passes
public class Game implements UI_EventListener
{

	// ************************ Interface Related Fields *********************//
	private GameEventListener mListener; // responsible for generating GameEvent that is used to pass info to UI
										 // and update interface

	// ************************ Interface Related Fields End*********************//

	// ************************ Model Related Fields *********************//

	private GameField mGameField; // main model class that contains the board and current and next shapes
	private GameState mGameState; // The class that keeps track of the current game state (is game active or 
								// paused, is game over, and so on. It also contains information that has user
								//current score, current level, keeps track of the opacity of the cover picture
		
	private GamePacer mTimer; // The class that triggers shape dropping down as time passes
	
	private AiGameField mAi; // AI that calculates the optimal position into which the current shape should be placed
	
	private ExecutorService executor; // executor service to keep time consuming interface updates and AI position calculations off the main thread
	private Future <Point> aiResult; // contains results of the AI calculations once it is ready
	private static int stepCounter;


	// ************************ Model Related Fields End *********************//

	// main Controller constructor
	// it initializes the model and game state
	public Game()
	{
		mGameState = new GameState(); // game state keeps track of various game characteristics such as current level, score, speed, as well as whether the game is paused
		mTimer = new GamePacer(mGameState.getWaitTime()); // trigger shape moving down when the game is played
		mGameField = new GameField(); // initialize new board and shapes
		executor = Executors.newCachedThreadPool();
	}
	
	
	// used by gamePacer and is called by drop. The shape is moved down by one square until it reaches the bottom.
	// When the shape reaches the bottom end of move events,
	// such as insertion of the shape into the board and line deletion happens. And then depending on whether 
	// there is place for next shape to be inserted
	// nextMove or gameOver event is triggered
	private synchronized void  move()
	{
		//System.out.println("Running move: " + runAiResult);
		if (mGameField != null && mGameState.isGamePlay())
		{
			if (mGameField.moveShapeDown())
				fireSuccessfulMoveEvent();
			else
			{
				fireLinesDeletedEvent(mGameField.deleteLines());
				mGameField.nextMove();
				executeAi();
				if (mGameField.startMove())
					fireStartTurnEvent();
				else
					fireGameOverEvent();
			}

		}
	}
	
	// run AI to calculate optimal position to place current shape
	private void executeAi()
	{
		if (mGameState.isShowHelp() || mGameState.isUseAi())
			aiResult = executor.submit(new AiCallable());
		else
			aiResult = null;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// prepare mGameField and mGameState for the start of the game
	public synchronized void initializeNewGame()
	{
		if (mGameState.isGameStarted()||mGameState.isGameOver())
		{
			mGameField.reset();
			mAi = null;
		}
		mGameState.startGame();
		mTimer.setDelay(mGameState.getWaitTime());
		fireStartGameEvent();
		stepCounter = 0;
	}
	
	// pause the game: stop the time and update interface
	public synchronized void pauseGame()
	{
		mTimer.stop();
		mGameState.pauseGame();
		GameEvent ev = new GameEvent(this, mGameState, null, null,
				mGameField.getCurrentShape().getShapeType(),null,  mGameField.getNextShape().getShapeType(), "pause game");
		executor.execute(new InterfaceUpdater(ev));
	}

	// unPause paused game
	public synchronized void unPauseGame()
	{
		if(mGameState.isGameStarted())
		{
		mGameState.restartGame();
		GameEvent ev = new GameEvent(this, mGameState, null, null,
				mGameField.getCurrentShape().getShapeType(), null,  mGameField.getNextShape().getShapeType(), "unPause game");
		executor.execute(new InterfaceUpdater(ev));
		mTimer.setDelay(mGameState.getWaitTime());
		mTimer.restart();
		
		}
	}
	
	
	// signals to the UI that the game started and sends relevant info for screen update in GameEvent
	// also runs the AI calculations on the background thread if necessary
	public void fireStartGameEvent()
	{
		if (mListener != null)
		{
			// run AI to calculate optimal position to place current shape
			executeAi();
			fireStartTurnEvent();
		}
		mTimer.start(); // start triggering game moves
	}
	
	
    // this method is triggered when the shape successfully moved left right, or down in response to user input, aiInput or timer
	public void fireSuccessfulMoveEvent()
	{
		if (mListener != null)
		{
			// when the shape moved successfully there is no need to update the state of the game. The state is only changed in response to end of turn events
			mGameState.setStateUpdated(false);
			mGameState.setGamePlayStatusUpdated(false);
			// the movement of the shape still has to be passed to GUI
			GameEvent ev = new GameEvent(this,mGameState, mGameField.getBoard(), mGameField.getCurrentShape().currentOrientationGlobal(),
					mGameField.getCurrentShape().getShapeType(), null,  mGameField.getNextShape().getShapeType(), "fireSuccessfulMovement");
			if (mGameState.isShowHelp() && aiResult!=null && aiResult.isDone())
			{
				showAiHints();
				ev.setHintsLocations(mAi.getHintsLocations());
			}
			executor.execute(new InterfaceUpdater(ev));
			
			if ( mGameState.isUseAi() && aiResult!= null && aiResult.isDone())
			{
				runAiResults(); // if the results of the optimal position calculations are ready move shape to the optimal position
			}
		}
	}

	// this method is triggered after the shape reached the bottom and was inserted into the board
	// Insertion of the shape into the board might lead to up to 4 lines being deleted 
	// @deletedLines is the indices of the lines being deleted or empty array if no lines were deleted
	public void fireLinesDeletedEvent(List<Integer> deletedLines)
	{
		if (mListener != null)
		{
			//runAiResult = false; // to prevent accidentally running previous aiResults for a new turn set flag to false
			mGameState.setStateUpdated(true);
			mGameState.setLinesDeleted( deletedLines.size());
			// update AI helper class in response to new shape being inserted into the board and possibly some lines being deleted
			if (mAi!= null)
				mAi.updateBoardHelper(mGameField.getBoard(), mGameField.getCurrentShape(), deletedLines.size());
			GameEvent ev = new GameEvent(this, mGameState, mGameField.getBoard(), null,
					mGameField.getCurrentShape().getShapeType(), null,  mGameField.getNextShape().getShapeType(), "fireLinesDeleted");
			executor.execute(new InterfaceUpdater(ev));	
		}
	}

	// at the beginning of every turn update the ui and rerun the AI calculations if necessary
	public synchronized void fireStartTurnEvent()
	{
		if (mListener != null)
		{
			stepCounter++;
			
			mGameState.startTurn(); 
			if (!mGameState.isGamePlay()) // if the game is paused after changing level
				mTimer.stop();
			GameEvent ev = new GameEvent(this, mGameState, mGameField.getBoard(), mGameField.getCurrentShape().currentOrientationGlobal(),  
					mGameField.getCurrentShape().getShapeType(), mGameField.getNextShape().nextShapeGlobal(),  mGameField.getNextShape().getShapeType(), "fireStartTurn");
			
			if (mGameState.isShowHelp() && aiResult!=null && aiResult.isDone())
			{
				showAiHints();
				ev.setHintsLocations(mAi.getHintsLocations());
			}
			executor.execute(new InterfaceUpdater(ev));
			
			if ( mGameState.isUseAi() && aiResult!= null && aiResult.isDone())
			{
				runAiResults(); // if the results of the optimal position calculations are ready move shape to the optimal position
			}
		}
	}

	
	public void fireGameOverEvent()
	{
		mGameState.gameOver();

		GameEvent ev = new GameEvent(this, mGameState , null, null,  
				mGameField.getCurrentShape().getShapeType(), null,  mGameField.getNextShape().getShapeType(), "fireGameOver");
		mTimer.stop();
		executor.execute(new InterfaceUpdater(ev));	
	}

	
	// update game in response to user or AI request
	public synchronized void onLeftKeyPressed()
	{
		if (mGameState.isGamePlay())
		{
			if (mGameField.moveShapeLeft())
				fireSuccessfulMoveEvent();
			else
				notifyError();
		}

	}

	// update game in response to user or AI request
	public synchronized void onRightKeyPressed()
	{
		if (mGameState.isGamePlay())
		{
			if (mGameField.moveShapeRight())
				fireSuccessfulMoveEvent();
			else
				notifyError();
		}
	}

	// update game in response to user or AI request
	public synchronized void onUpKeyPressed()
	{
		if (mGameState.isGamePlay())
		{
			if (mGameField.rotate())
				fireSuccessfulMoveEvent();
			else
				notifyError();
		}
	}

	// update game in response to user request
	public synchronized void onDownKeyPressed()
	{
		if (mGameState.isGamePlay())
		{
			mGameField.drop();
			move();
		}
	}

	// makes beeping sound when the shape cannot be moved to the desired position 
	private void notifyError()
	{
		Toolkit.getDefaultToolkit().beep();
	}

	// UI listener that updates ui in responce to model changes
	public void setModelListener(GameEventListener listener)
	{
		mListener = listener;
	}

	@Override
	// play button exhibits different behaviors depending on the state the game is in
	public synchronized void onPlayButtonPressed(PlayButtonPressEvent e)
	{
		if (mGameState.isGameStarted() == false && mGameState.isGamePlay() == false)
		{
			initializeNewGame();
		} else if (mGameState.isGamePlay() == false)
		{
			unPauseGame();
		} else if (mGameState.isGamePlay() == true)
		{
			pauseGame();
		}

	}


	@Override
	public synchronized void onKeyPress(KeyBindingEvent e)
	{
		String actionName = e.getActionType();
		if (actionName.equals(ShapeMoveAction.ACTN_ROTATE))
			onUpKeyPressed();
		else if (actionName.equals(ShapeMoveAction.ACTN_DROP))
			onDownKeyPressed();
		else if (actionName.equals(ShapeMoveAction.ACTN_LEFT))
			onLeftKeyPressed();
		else if (actionName.equals(ShapeMoveAction.ACTN_RIGHT))
			onRightKeyPressed();
	}
	
	// a timer class that triggers currentShape move down events
	private class GamePacer extends Timer
	{
		private GamePacer(int delay) {
			super(delay, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					move();
				}
			});
		}
	}
	
	// This function determines which hint arrows to show and the best locations to show them based on the results
	// of AI calculations and current shape locations
	private synchronized void showAiHints()
	{
		try
		{
			int requiredRotations = aiResult.get().getY();
			int currentRotations = mGameField.getCurrentShape().getOrientation();
			int reqMovement = aiResult.get().getX();
			int currentMovement = mGameField.getCurrentShape().getLocation().getX();
			mAi.setHintsLocations(mGameField.getCurrentShape().getHintsLocations());
			boolean shouldRotate = requiredRotations!= currentRotations && requiredRotations - currentRotations <4;
			if (! (shouldRotate))
				mAi.getHintsLocations().set(1, new Point(-1,-1));
			
			if (reqMovement != currentMovement)
				if (reqMovement > currentMovement)
					mAi.getHintsLocations().set(0, new Point(-1,-1));
				else
					mAi.getHintsLocations().set(2, new Point(-1,-1));
			else
			{
				mAi.getHintsLocations().set(0, new Point(-1,-1));
				mAi.getHintsLocations().set(2, new Point(-1,-1));
				
			}
			if(shouldRotate || reqMovement != currentMovement)
				mAi.getHintsLocations().set(3, new Point(-1,-1));
		}
		catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// move the current shape to the calculated position if in Auto mode
	private synchronized void  runAiResults ()
	{
        
		try {
			
        	int requiredRotations = aiResult.get().getY();
        	int currentRotations = mGameField.getCurrentShape().getOrientation();
        
			if ( requiredRotations!= currentRotations && requiredRotations - currentRotations <4)
				if(mGameState.isGamePlay())
				{
					//Thread.sleep(100);
					onUpKeyPressed();
					
				}
			
        	int reqMovement = aiResult.get().getX();
			int currentMovement = mGameField.getCurrentShape().getLocation().getX();
			if (reqMovement > currentMovement)
			{
				onRightKeyPressed();
				
			}
			else if (reqMovement < currentMovement)
			{
				onLeftKeyPressed();
				
			}
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        
        
	}
	
	// small class for running AI calculations on the background thread
	private class AiCallable implements Callable<Point>
	{
		@Override
		public Point call() throws Exception {
			mAi = new AiGameField(mGameField, mAi==null? null: mAi.getBoardHelper());
			return mAi.optimize();
		}
	}

	// update interface without stalling other events
	private class InterfaceUpdater implements Runnable
	{
		private GameEvent ev;
		
		public InterfaceUpdater (GameEvent ev)
		{
			this.ev = ev;
		}
		
		public void run()
		{
			mListener.gameEventOccurred(ev);
		}
	}

	@Override
	public synchronized void onAIButtonPressed(boolean useAI) {
		mGameState.setUseAi(useAI);
		if ((mGameState.isShowHelp() || mGameState.isUseAi()) && aiResult ==null)
			aiResult = executor.submit(new AiCallable());
	}


	@Override
	public synchronized void onShowHintsButtonPressed(boolean showHints) {
		mGameState.setShowHelp(showHints);
		if ((mGameState.isShowHelp() || mGameState.isUseAi()) && aiResult == null)
			aiResult = executor.submit(new AiCallable());
		
	}


	@Override
	public void onHelpButtonPressed() {
		if (mGameState.isGamePlay())
			pauseGame();
	}
		

	

}
