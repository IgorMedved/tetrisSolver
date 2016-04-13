package model;



import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidShapeException;

import model.shapes.Shape;

public class GameField
{
	
	
	private static boolean isFilled;
	
	private Board mBoard;
	private Shape mCurrentShape;
	private Shape mNextShape;
	
	
	public GameField ()
	{
		mBoard = new Board();
		// a shape generated with generateRandom method should never generate InvalidShapeException!
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
	
	public boolean startMove ()
	{
		return mBoard.insertShapeIntoBoard(mCurrentShape);
	}
	
	private boolean moveShape(Shape newShape, String message)
	{
		List<Point> globalPoints = mCurrentShape.currentOrientationGlobal();
		mBoard.remove(globalPoints);
		
		
		
		if (mBoard.insertShapeIntoBoard(newShape))
		{
			mCurrentShape= newShape;
			return true;
		}
		else
		{
			mBoard.insert(globalPoints, mCurrentShape.getShapeType());
			
			return false;
		}
	}
	
	public boolean rotate()
	{
		return moveShape (mCurrentShape.getNextOrientation(), "Can't Rotate");
	}
	
	public boolean moveShapeLeft ()
	{
		return moveShape (mCurrentShape.left(), "Can't move left");
	}
	
	public boolean moveShapeRight()
	{
		return moveShape (mCurrentShape.right(), "Can't move right");
	}
	
	public boolean moveShapeDown()
	{
		return moveShape (mCurrentShape.down(), "Can't move down");
	}
	
	
	
	public boolean nextMove()
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
		
		
		return startMove();
		
	}
	
	
	
	public void drop()
	{
		while (moveShapeDown());
	}
	
	
	

	
	public List <Integer> deleteLines()
	{
		return mBoard.deleteLines();
	}
	
	
	
	
	
	

	public String boardToString()
	{
		return mBoard.convertBoardToString();
	}
	
	
	
/*	
	public static void printGameField (GameField field)
	{
		System.out.println();
		
		for (int i = field.getHeight() -1; i>=0; i --)
		{
			for (int j = 0; j < field.getWidth(); j ++)
			   if (field.getContentAtPosition(j,i)==FILLED)
			   	 System.out.print("x");
			   else
			     System.out.print("0");
			     
			System.out.println();
		}
		
		System.out.println();
		
	}*/
}