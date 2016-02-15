package model.shapes;

import java.util.Arrays;
import java.util.List;

import model.GameField;
import model.Point;

public class Shape_S extends Shape
{
	private final static Point[] ORIENTATION1 = new Point[]{new Point (1,1), new Point (2,1), new Point (2,2), new Point (3,2)};
	private final static Point[] ORIENTATION2 = new Point[]{new Point (3,1), new Point (2,2), new Point (3,2), new Point (2,3)};
	private final static Point[] ORIENTATION3 = new Point[]{new Point (1,2), new Point (2,2), new Point (2,3), new Point (3,3)};
	private final static Point[] ORIENTATION4 = new Point[]{new Point (2,1), new Point (1,2), new Point (2,2), new Point (1,3)};
	
	@Override
	protected void putShapeInOrientation(int orientation)
	{
		mShape = getPositionForOrientation(orientation);
		
		
		
	}
	
	public Shape_S ()
	{
		mShape = getPositionForOrientation(0);
		mOrientation = 0;
	}
	
	public Shape_S (int orientation)
	{
		mOrientation = orientation;
		mShape = getPositionForOrientation(orientation);
	}
	
	private static List<Point> getPositionForOrientation(int orientation)
	{
		switch (orientation)
		{
			case 0:
			return Arrays.asList(ORIENTATION1);
			
			case 1:
			return Arrays.asList(ORIENTATION2);
			
			case 2:
				return Arrays.asList(ORIENTATION3);
				
			case 3:
				return Arrays.asList(ORIENTATION4);
				
			
			default:
			return null;
		}
		
	}
	
	
	

	
	
	@Override
	protected void empty()
	{
		for (int i = mShape.size() - 1; i >=0; i--)
			mShape.remove(i);
	}
	
	@Override
	public boolean rotateShape()
	{
		mOrientation = (mOrientation +1)%4;
		mShape = getPositionForOrientation (mOrientation);
		
		return true;
		
	}
	
	public void printInitialShape ()
	{
		printShapeForOrientation(0);
	}
	
	public static void printShapeForOrientation (int orientation)
	{
		List<Point> shape = getPositionForOrientation(orientation); 
		
		
		
		GameField field = GameField.convertPointsToGameField(shape, 5,4);
		
		GameField.printGameField (field);
	}
	
	public void printCurrentOrientation ()
	{
		GameField field = GameField.convertPointsToGameField(mShape, 5,4);
		GameField.printGameField (field);
	}
	
	public void printNextOrientation()
	{
		GameField field = GameField.convertPointsToGameField(getPositionForOrientation(mOrientation+1), 5,4);
		
		
		GameField.printGameField (field);
	}
	
	public static void printAllOrientationsForShape()
	{
		for (int i = 0; i < 4; i++)
			printShapeForOrientation (i);
	}

	@Override
	public Shape getNextOrientation()
	{
		
		return new Shape_S((mOrientation+1)%4);
	}
}
