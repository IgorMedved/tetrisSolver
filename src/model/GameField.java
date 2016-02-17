package model;



import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidShapeException;

import model.shapes.Shape;

public class GameField
{
	public static final boolean FILLED = true;
	public static final boolean EMPTY = false;
	
	public static final int BOARD_SIZE_X = 12;
	public static final int BOARD_SIZE_Y = 18;
	
	private static boolean isFilled;
	
	private List<List<Boolean>> mGameField;
	private Shape mCurrentShape;
	private Shape mNextShape;
	private Point mCurrentShapeLocation;
	
	public GameField (int sizeX, int sizeY)
	{
		initializeGameField (sizeX, sizeY );
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
		mCurrentShapeLocation = initialPoint();
	
	}
	
	private Point initialPoint()
	{
		return new Point (3, BOARD_SIZE_Y-4);
	}
	
	private void initializeGameField(int sizeX, int sizeY)
	{
		mGameField = new ArrayList<>();
		List<Boolean> xRow;
		
		for (int i = 0; i < sizeY ; i++)
		{
			xRow = new ArrayList<>();
			for (int j = 0; j < sizeX; j ++ )
			{
				xRow.add(EMPTY);
				
			}
			mGameField.add(xRow);
		}
		
	}
	
	public static GameField convertShapeToGameField(Shape shape, int sizeX, int sizeY)
	{
		GameField field = new GameField(sizeX, sizeY);
		
		final List<Point> points = shape.currentOrientation();
		
		
		
		for(Point point: points)
		{
			field.fillPoint (point.getX(), point.getY());
		}
		
		
		
		return field;
	}
	
	public void startMove ()
	{
		if (insertShapeIntoGameField(mCurrentShape, mCurrentShapeLocation))
			printGameField(this);
		else
			gameOver();
			
	}
	
	public boolean moveLeft ()
	{
		Point left = mCurrentShapeLocation.left();
		
		List<Point> globalPoints = convertShapeToGlobal(mCurrentShape, mCurrentShapeLocation);
		remove(globalPoints);
		if (insertShapeIntoGameField(mCurrentShape, left))
		{
			printGameField(this);
			mCurrentShapeLocation = left;
			return true;
		}
		else
		{
			insert(globalPoints);
			System.out.println("Can't move left");
			return false;
		}
		
	}
	
	public boolean moveRight()
	{
		Point right = mCurrentShapeLocation.right();
		
		List<Point> globalPoints = convertShapeToGlobal(mCurrentShape, mCurrentShapeLocation);
		remove(globalPoints);
		if (insertShapeIntoGameField(mCurrentShape, right))
		{
			printGameField(this);
			mCurrentShapeLocation = right;
			return true;
		}
		else
		{
			insert(globalPoints);
			System.out.println("Can't move right");
			return false;
		}
	}
	
	public boolean moveDown()
	{
		Point down = mCurrentShapeLocation.down();
		
		List<Point> globalPoints = convertShapeToGlobal(mCurrentShape, mCurrentShapeLocation);
		remove(globalPoints);
		if (insertShapeIntoGameField(mCurrentShape, down))
		{
			printGameField(this);
			mCurrentShapeLocation = down;
			return true;
		}
		else
		{
			insert(globalPoints);
			System.out.println("Can't move down");
			deleteLines();
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
		mCurrentShapeLocation = initialPoint();
		
		startMove();
		
	}
	
	private void deleteLines()
	{
		boolean linesDeleted =false;
		for (int row = 0; row <BOARD_SIZE_Y; row++)
			linesDeleted = deleteLine(row);
		
		if (linesDeleted)
			printGameField(this);
			
	}
	
	private boolean deleteLine (int row)
	{
		for (int col = 0; col <BOARD_SIZE_X; col++)
			if (!mGameField.get(row).get(col))
				return false;
		
		return true;
	}
	
	public void drop()
	{
		while (moveDown());
	}
	
	public boolean rotate()
	{
		Shape rotatedShape = mCurrentShape.getNextOrientation();
		
		List<Point> globalPoints = convertShapeToGlobal(mCurrentShape,mCurrentShapeLocation);
		remove (globalPoints);
		
		if (insertShapeIntoGameField(rotatedShape, mCurrentShapeLocation))
		{
			printGameField(this);
			mCurrentShape = rotatedShape;
			return true;
		}
		else
		{
			insert(globalPoints);
			System.out.println("Can't rotate");
			return false;
		}
		
		
	}
	
	private void insert (List<Point> globalPoints)
	{
		for (Point point : globalPoints)
		{
			fillPoint(point);
		}
	}
	
	private void remove(List<Point> globalPoints)
	{
		for (Point point : globalPoints)
		{
			
			removePoint(point);
		}
	}
	
	private void gameOver()
	{
		System.out.println("Game Over!");
	}
	
	public boolean insertShapeIntoGameField (Shape shape, Point location )
	{
		List<Point> globalPoints = convertShapeToGlobal(shape, location);
		
		if (shapeOutOfBounds(globalPoints))
			return false;
		else if (detectCollision (globalPoints))
			return false;
		
		insert(globalPoints);
		
		return true;
	}
	
	private boolean shapeOutOfBounds (List<Point> globalPoints)
	{
		
		for (Point point:globalPoints)
			if (point.outOfBound())
				return true;
		
		return false;
			
	}
	
	private boolean detectCollision (List<Point> globalPoints)
	{
		for (Point point: globalPoints)
			if (mGameField.get(point.getY()).get(point.getX()))
				return true;
		return false;
	}
	
	private List<Point> convertShapeToGlobal(Shape shape, Point location)
	{
		List<Point> shapePoints = shape.currentOrientation();
		List<Point> globalPoints = new ArrayList<Point>();
		
		for (Point point:shapePoints)
		{
			
			globalPoints.add(new Point (point.getX()+location.getX(), point.getY()+ location.getY()));
		}
		
		return globalPoints;
	}
	
	public void fillPoint (int x, int y)
	{
		mGameField.get(y).set(x,FILLED);
	}
	
	public void fillPoint (Point point)
	{
		fillPoint(point.getX(), point.getY());
	}
	public void removePoint (int x, int y)
	{
		mGameField.get(y).set(x, EMPTY);
	}
	
	public void removePoint (Point point)
	{
		removePoint (point.getX(), point.getY());
	}
	
	public int getHeight()
	{
		return mGameField.size();
	}
	
	public int getWidth()
	{
		return mGameField.get(0).size();
	}
	
	public boolean getContentAtPosition (int x, int y)
	{
		return mGameField.get(y).get(x);
	}
	
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
		
	}
}