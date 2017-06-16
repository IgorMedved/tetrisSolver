package ai;

import java.util.List;

import exceptions.InvalidShapeException;
import tetris_model.Board;
import tetris_model.GameField;
import tetris_model.Point;
import tetris_model.contracts.TetrisContract;
import tetris_model.shapes.AvailableMovements;
import tetris_model.shapes.BlockShapeDefinitions;
import tetris_model.shapes.Borders;
import tetris_model.shapes.Shape;

// This class evaluates all possible positions that result from dropping current and next shapes into the board
// and returns the best dropping position for current shape
public class AiGameField extends GameField {
	
	private BoardHelper mBoardHelper; // Board helper contains information about each column height
										// and gaps in each row
	private List <Point> hintsLocations; // contains locations of hint arrows for the current shape
	
	// main constructor
	public AiGameField (GameField copy, BoardHelper helper)
	{
		// copy current position so that the copied board can be modified for the evaluation purposes
		
		mMainBoard = new Board(copy.getBoard());
		mCurrentShape = Shape.shapeCopy(copy.getCurrentShape());
		mNextShape = Shape.shapeCopy(copy.getNextShape());
		if (helper == null) // create new Board helper if necessary
		{
			mBoardHelper = new BoardHelper(mMainBoard.getBoard(), false);
		}
		else
		{
			mBoardHelper = helper;
		}
	}
	
	// it is much faster to update existing board helper than creating a new one use this
	// function whenever possible
	public void updateBoardHelper (List<List<Integer>> board, Shape droppedShape, int numLinesDeleted)
	{
		mBoardHelper.updateHeights(board, droppedShape, numLinesDeleted);
	}
	
	public BoardHelper getBoardHelper()
	{
		return mBoardHelper;
	}
	
	// This function returns an optimization vector
	// xValue of the @Point contains the xCoordinate of where current shape should move
	// yValue of the @Point contains the orientation that the current shape should take
	// The function works in the following way. The two outer loops produce every possible drop
	// position for the current shape
	// The two inner loops then produce every possible nextShape drop on top of the currentShape drop
	// Every position produced in this manner is then evaluated and compared to the best position so far
	// The position and orientation of the dropped current shape that resulted in the best evaluation
	// score is returned as a result
	public Point optimize()
	{
		//System.out.println("\n\n\n");
		long start = System.nanoTime();
		Shape droppedCurrentShape = null; // stores where the current shape would end up after drop
		Shape droppedNextShape = null; // stores where nextShape would end up after drop
		final int[] linesDeleted = new int[1]; // lines deleted contains just one element. The array is used so that it can be passed by reference
		linesDeleted[0] = 0;
		int evaluationScore = Integer.MAX_VALUE; // each board position is evaluated using certain 
						// metrics. The best position has the lowest evaluationScore
		int evaluationScoreStep0 = Evaluator.evaluate(mBoardHelper);
		int heightStep1 = Integer.MAX_VALUE; // when two positions have the same score use maxHeight
									// as a tie breaker (the lower the maxHeight the better)
		// number of distinguishable orientations for current shape 
		int currentFigureRotations = BlockShapeDefinitions.getNumRotations(mCurrentShape.getShapeType());
		// number of distinguishable orientations for next Shape
		int nextFigureRotations = BlockShapeDefinitions.getNumRotations(mNextShape.getShapeType());
		// initialize movement vector to current xCoordinate and orientation
		Point movement = new Point(mCurrentShape.getLocation().getX(), mCurrentShape.getOrientation());
		
		// first outer loop rotate the current shape into every orientation
		for (int rotation = 0; rotation < currentFigureRotations; rotation ++)
		{
			int currentFigureNumMovementLeft = AvailableMovements.minLeft(mCurrentShape.getShapeType(), rotation) - mCurrentShape.getLocation().getX();
			int currentFigureNumMovementRight = AvailableMovements.maxRight(mCurrentShape.getShapeType(), rotation) - mCurrentShape.getLocation().getX();
			// second outer loop move current shape from leftMost possible position 
			// to the right most possible position and drop it
			for (int numMovement = currentFigureNumMovementLeft; numMovement <= currentFigureNumMovementRight; numMovement++)
			{
				droppedCurrentShape = Shape.shapeCopy(mCurrentShape);
				BoardHelper step1Helper = new BoardHelper(mBoardHelper);
				Board step1Board = produceDrop(mMainBoard, mCurrentShape, rotation, numMovement- currentFigureNumMovementLeft, numMovement , false, step1Helper, droppedCurrentShape, linesDeleted);
				if (step1Board == null)
					return movement; // can't insert shape into board
				step1Helper.updateHeights(step1Board.getBoard(), droppedCurrentShape, linesDeleted[0]);
				/*if (step1Helper.getMaxHeight() > mMainBoard.getHeight()-5) // do not evaluate inferior positions
					if (Evaluator.evaluate(step1Helper) >evaluationScoreStep0+500)
						continue;*/
				// first inner loop rotate next shape into every possible orientation
				inner: for (int rotation2 = 0; rotation2 < nextFigureRotations; rotation2 ++)
				{
					int nextFigureNumMovementLeft = AvailableMovements.minLeft(mNextShape.getShapeType(), rotation2) - mNextShape.getLocation().getX();
					int nextFigureNumMovementRight = AvailableMovements.maxRight(mNextShape.getShapeType(), rotation2) - mNextShape.getLocation().getX();
					// second inner loop move next shape into every possible position from left
					// to right and drop
					// Evaluate resulting position, and save the orientation of the current
					// shape if it resulted in the best score so far
					for (int numMovement2 = nextFigureNumMovementLeft; numMovement2 <= nextFigureNumMovementRight; numMovement2++)
					{
						droppedNextShape = Shape.shapeCopy(mNextShape);
						BoardHelper step2Helper = new BoardHelper(step1Helper);
						Board step2 = produceDrop(step1Board, mNextShape, rotation2, numMovement2 - nextFigureNumMovementLeft, numMovement2 , true, step2Helper, droppedNextShape, linesDeleted);
						if (step2 == null)
							break inner;
						step2Helper.updateHeights(step2.getBoard(), droppedNextShape, linesDeleted[0]);
						step2Helper.createCounter(step2.getBoard());
						
						int currentEvaluation = Evaluator.evaluate (step2Helper);
						//step2Helper.printHeights();
						//System.out.println("Evaluation is " + currentEvaluation);
						// use evaluation score first
						if (currentEvaluation < evaluationScore)
						{
							evaluationScore = currentEvaluation;
							movement = new Point(numMovement+ mCurrentShape.getLocation().getX(), rotation);
							heightStep1 = step1Helper.getMaxHeight();
							
						}
						// and if it's the same as the previous best
						else if (currentEvaluation == evaluationScore)
						{
							// use maximum column height from a board produced in outer loop as
							// a tie-breaker
							if (heightStep1 <step1Helper.getMaxHeight())
							{
								heightStep1 = step1Helper.getMaxHeight();
								movement = new Point(numMovement+ mCurrentShape.getLocation().getX(), rotation);
							}
						}
					}
				}
			}
		}
		long end = System.nanoTime();
		System.out.println("\n\n\n Evaluation takes " + (end-start));
		return movement;
	}
	
		
	// helper method that returns a board that would result from @board when
	// dropping the @figure at a given @rotation and @col into it)
	public static Board produceDrop (Board board, Shape figure, int rotation, int col, int numMovement, boolean countGaps, BoardHelper helper, Shape dropShape, int[] linesDeleted)
	{
		Board dropBoard = new Board (board.getBoard()); // get a copy of the board
		int yShift;
		
		// yShift - the lowest y coordinate to which @figure can drop in orientation @rotation and xCoordinate col 
		yShift= AvailableMovements.maxDown(figure.getShapeType(), rotation, col, helper.getHeights());
		// move dropShape to new point and rotate it to correct orientation @rotation
		Point desiredInsertionLocation = new Point (figure.getLocation().getX()+ numMovement , yShift);
		
		//System.out.println("Trying to insert figure " + figure.getShapeType() + " rotations " + rotation + " in col " + col);
		// check that the current shape can actually be inserted into the board at a desired location
		if (helper.getMaxHeight() < figure.getLocation().getY()) // quick check that insures that the shape can move into
																// the desired location
		{
			dropShape.setLocation(desiredInsertionLocation);
			dropShape.setOrientation(rotation);
			//System.out.println("Setting location normally");
			//helper.printHeights();
		}
		else
		{
			//System.out.println("Setting location with checks");
			//helper.printHeights();
			
			if (!dropBoard.canInsert(figure)) // shape cannot even be inserted at location where it's at
			{
				//System.out.println("Cannot insert at starting location");
				return null; 
			}
			else // shape can be inserted at the starting location but we still need to check if it can reach desired location
			{
				// check if we can rotate the shape into desired orientation
				boolean canReachOrientation = false;
				//System.out.println("Can insert in starting location");
				if (figure.getOrientation() == rotation) // it is possible that we are already in desired orientation
					canReachOrientation = true;
				else
				{
					for (int i = 1; i < BlockShapeDefinitions.getNumOrientations(); i++)
					{
						int currentRotations = BlockShapeDefinitions.convertToValidOrientation(figure.getOrientation() + i);
						if (!dropBoard.canInsert(Shape.shapeCopyInOrientation(figure, currentRotations)))
						{
							break;
						}
						else if (currentRotations == rotation)
						{
							canReachOrientation = true;
							break;
						}
					}
				}
				
				
				if (canReachOrientation) // now check that we can move shape into the desired location
				{
					//System.out.println("can rotate to desired orientation");
					dropShape.setOrientation(rotation);
				
					int bottom = dropShape.getBottomEdge(); // yCoordinate of bottom edge
					int left = dropShape.getLeftEdge(); // xCoordinate of left edge
					int right = dropShape.getRightEdge();// xCoordinate of right edge
					int [] shapeBottom = dropShape.lowestPointCoordinates();
					int edge;
					int shift;
					boolean canReachLocation = true;
					if (col < left) // if we are are left of the desired location
					{
						// need to move left 
						edge = dropShape.getBottomLeft(); // yCoordinate of lowestPoint in left column
						shift = shapeBottom[1]-left;
						//System.out.println("Bottom " + bottom +  " edge " + edge);
						for (int i = left-1; i>=col; i--)
						{
							if (!(helper.getHeights().get(i) < edge && helper.getHeights().get(i+shift) < bottom))
							{
								canReachLocation = false;
								break;
							}
						}
					
					}
					else if (col > left) // if we are right of the desired location
					{
						edge = dropShape.getBottomRight();
						shift = shapeBottom[shapeBottom[0]] - dropShape.getRightEdge();
						for (int i = right; i <= col+right-left; i++ )
						{
							if (!(helper.getHeights().get(i) < edge && helper.getHeights().get(i+shift)< bottom))
							{
								canReachLocation = false;
								break;
							}
						}
					}
					if (canReachLocation)
					{
						//System.out.println("Can insert!!!");
						dropShape.setLocation(desiredInsertionLocation);
					}
					else
					{
						//System.out.println("Cannot move to the desired location");
					}
				
				}
				else
				{
					//System.out.println("Cannot insert into desired orientation");
				}
			}
		}
		/*if (helper.getMaxHeight() >= TetrisContract.INITIAL_POINT.getY())
		{
			canInsert = dropBoard.canInsert(figure);
			if (canInsert)
			{
				// and if it can be inserted check that it can be moved to the insertion position
				canInsertAtLocation = dropBoard.canInsert(dropShape);
				if (!canInsertAtLocation)
				{
					// if we can't insert at the desired position, but can inserte at the initial
					// position treat it treat it like inserting the figure at initial position
					// and NOT dropping it
					dropShape.setLocation(new Point(figure.getLocation().getX(), figure.getLocation().getY()));
					dropShape.setOrientation(figure.getOrientation());
				}
			}
			else
				return null;
		}*/
		
		
		dropBoard.insert(dropShape.currentOrientationGlobal(), dropShape.getShapeType());
		Borders border = AvailableMovements.getShapeBorder(dropShape.getShapeType(), rotation);
		linesDeleted[0] = dropBoard.deleteLines(border.yDown+yShift, border.yUp + yShift).size();
		return dropBoard;
	}

	public void setHintsLocations(List<Point> hintsLocations2) {
		hintsLocations = hintsLocations2;
	}
	
	public List<Point> getHintsLocations()
	{
		return hintsLocations;
	}
		

}
