package tetris_model;

import java.util.List;

import exceptions.InvalidShapeException;
import tetris_model.shapes.AvailableMovements;
import tetris_model.shapes.Borders;
import tetris_model.shapes.Shape;

// this is the main Model class
// It contains the main board and current and next shapes
// It handles the movement of the current shape in response to user input as well as startTurn and endTurn events, such as generation of new shapes 
// at the beginning of each turn,
// line deletion for the filled rows and so on

public class GameField  {
	protected Board mMainBoard;
	protected Shape mCurrentShape;
	protected Shape mNextShape;
	
	public GameField ()
	{
		mMainBoard = new Board(BoardType.MAINBOARD); // create a new tetris Board
		prepareField(); // randomly generate shapes for mCurrentShape and mNext shape
	}
	
	
	public Shape getCurrentShape()
	{
		return mCurrentShape;
	}

	
	public List<List<Integer>> getBoard()
	{
		return mMainBoard.getBoard();
	}
	
	
	public Shape getNextShape()
	{
		return mNextShape;
	}
	
	protected void prepareField()
	{
		try
		{
			mCurrentShape = Shape.generateRandom();
			mNextShape = Shape.generateRandom();
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// this methods runs after the game was ended
	public void reset()
	{
		mMainBoard.clear();
		prepareField();
	}
	
	// check if there is enough space to insert a shape a the beginning of the turn
	public boolean startMove ()
	{
		return mMainBoard.canInsert(mCurrentShape);
	}
	
	// rotate shape (runs when the up button is pressed)
	public boolean rotate()
	{
		boolean canMove = mCurrentShape.canRotate(mMainBoard);
		if (canMove)
			mCurrentShape.rotateShape();
		return canMove;
	}
	
	// move shape left (usually in response to left button press)
	public boolean moveShapeLeft ()
	{
		boolean canMove = mCurrentShape.canMoveLeft(mMainBoard);
		if (canMove)
			mCurrentShape.moveShapeLeft();
		return canMove;
	}
	
	// move shape right (usually in response to left button press)
	public boolean moveShapeRight()
	{
		boolean canMove = mCurrentShape.canMoveRight(mMainBoard);
		if (canMove)
			mCurrentShape.moveShapeRight();
		return canMove;
	}
	
	// move shape down by one square, if the shape reaches the bottom it is inserted into the board
	public  boolean moveShapeDown()
	{
		boolean canMove = mCurrentShape.canMoveDown(mMainBoard);
		if (canMove)
			mCurrentShape.moveShapeDown();
		else
			mMainBoard.insertShapeIntoBoard(mCurrentShape);
		return canMove;
	}
	
	// make next shape a current shape and generate a new shape for the next shape
	public void nextMove()
	{
		mCurrentShape = mNextShape;
		try
		{
			mNextShape = Shape.generateRandom();
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public Board getMainBoard ()
	{
		return mMainBoard;
	}
	
	// moves the shape to the very bottom one step at a time and and inserts it when the shape reaches the bottom
	public void drop()
	{
		while (moveShapeDown());
	}
	
	// The fully filled rows are deleted, and all the filled squares above the deleted rows move down.
	// The method returns the indices of the deleted lines or empty array if no lines are deleted
	// Only the rows in which the current figure ended up could be deleted, so deletion process is optimized
	public List <Integer> deleteLines()
	{
		return mMainBoard.deleteLines(mCurrentShape.getBottomEdge(), mCurrentShape.getTopEdge());
	}
	
	// legacy method from before the GUI was added.
	public String boardToString()
	{
		return mMainBoard.convertBoardToString();
	}
	

	
}
