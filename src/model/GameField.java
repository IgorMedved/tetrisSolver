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
	
	
	public GameField (int sizeX, int sizeY)
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
	
	
	
	
	
	public void startMove ()
	{
		if (mBoard.insertShapeIntoBoard(mCurrentShape))
			//printGameField(this);
			notify();
		else
			gameOver();
			
	}
	
	public boolean moveShapeLeft ()
	{
		Shape left = mCurrentShape.left();
		
		List<Point> globalPoints = mCurrentShape.currentOrientationGlobal();
		mBoard.remove(globalPoints);
		if (mBoard.insertShapeIntoBoard(left))
		{
			//printGameField(this);
			mCurrentShape = left;
			return true;
		}
		else
		{
			mBoard.insert(globalPoints);
			System.out.println("Can't move left");
			return false;
		}
		
	}
	
	public boolean moveRight()
	{
		Shape right = mCurrentShape.right();
		
		List<Point> currentGlobalPoints = mCurrentShape.currentOrientationGlobal();
		mBoard.remove(currentGlobalPoints);
		if (mBoard.insertShapeIntoBoard(right))
		{
			//printGameField(this);
			mCurrentShape = right;
			return true;
		}
		else
		{
			mBoard.insert(currentGlobalPoints);
			System.out.println("Can't move right");
			return false;
		}
	}
	
	public boolean moveDown()
	{
		Shape down = mCurrentShape.down();
		
		List<Point> currentGlobalPoints = mCurrentShape.currentOrientationGlobal();
		mBoard.remove(currentGlobalPoints);
		if (mBoard.insertShapeIntoBoard(down))
		{
			//printGameField(this);
			mCurrentShape = down;
			return true;
		}
		else
		{
			mBoard.insert(currentGlobalPoints);
			System.out.println("Can't move down");
			if (mBoard.deleteLines())
				notify();
			nextMove();
			return false;
		}
	}
	
	private void nextMove()
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
		
		
		startMove();
		
	}
	
	
	
	public void drop()
	{
		while (moveDown());
	}
	
	public boolean rotate()
	{
		Shape rotatedShape = mCurrentShape.getNextOrientation();
		
		List<Point> globalPoints = mCurrentShape.currentOrientationGlobal();
		mBoard.remove (globalPoints);
		
		if (mBoard.insertShapeIntoBoard(rotatedShape))
		{
			//printGameField(this);
			mCurrentShape = rotatedShape;
			return true;
		}
		else
		{
			mBoard.insert(globalPoints);
			System.out.println("Can't rotate");
			return false;
		}
		
		
	}
	

	
	private void gameOver()
	{
		System.out.println("Game Over!");
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