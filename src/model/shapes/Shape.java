package model.shapes;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Board;
import model.GameField;
import model.Point;
import exceptions.InvalidShapeException;

public class Shape
{
	
	private int mOrientation;
	private int mShapeType;
	private Point mLocation;
	
	// create shape in particular orientation
	// if shapeType is not valid throw Invalid Shape Exception
	public Shape (int shapeType, int orientation) throws InvalidShapeException
	{
		this (shapeType, orientation, initialPoint());
	}
	
	public Shape (int shapeType, int orientation, Point location) throws InvalidShapeException
	{
		if (!BlockShapeDefinitions.isValidShapeType(shapeType))
			throw new InvalidShapeException(shapeType);
		
		else
		{
			mShapeType = shapeType;
			mOrientation = BlockShapeDefinitions.convertToValidOrientation(orientation);
			mLocation = initialPoint();
		}
	}
	
	public Shape (int shapeType) throws InvalidShapeException
	{
		this (shapeType, 0);
	}
	
	public void rotateShape ()
	{
		mOrientation = BlockShapeDefinitions.getNextOrientation(mOrientation);
	}
	
	public void moveShapeLeft ()
	{
		mLocation = mLocation.left();
	}
	
	public void moveShapeRight()
	{
		mLocation = mLocation.right();
		
	}
	

	
	public void moveShapeDown()
	{
		mLocation = mLocation.down();
	}
	
	public Shape left()
	{
		try
		{
			return new Shape(mShapeType, mOrientation, mLocation.left());
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Shape right()
	{
		try
		{
			return new Shape(mShapeType, mOrientation, mLocation.right());
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Shape down()
	{
		try
		{
			return new Shape(mShapeType, mOrientation, mLocation.down());
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// this method always returns valid shape
	public Shape getNextOrientation() 
	{
		try
		{
			return new Shape (mShapeType, BlockShapeDefinitions.getNextOrientation(mOrientation));
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private List<Point> initialOrientation ()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, 0);
	}
	
	private List<Point> currentOrientation()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, mOrientation);
	}
	
	private List<Point> nextOrientation()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, BlockShapeDefinitions.getNextOrientation(mOrientation));
	}
	
	public List<Point> initialOrienationGlobal()
	{
		List<Point> globalPoints = new ArrayList<>();
		List<Point> localPoints = initialOrientation();
		
		for (Point point :localPoints)
		{
			globalPoints.add(new Point(point.getX()+ mLocation.getX(), point.getY()+mLocation.getY()));
		}
		
		return globalPoints;
	}
	
	public List<Point> currentOrientationGlobal()
	{
		List<Point> globalPoints = new ArrayList<>();
		List<Point> localPoints = currentOrientation();
		
		for (Point point :localPoints)
		{
			globalPoints.add(new Point(point.getX()+ mLocation.getX(), point.getY()+mLocation.getY()));
		}
		
		return globalPoints;
	}
	
	public List<Point> nextOrientationGlobal()
	{
		List<Point> globalPoints = new ArrayList<>();
		List<Point> localPoints = nextOrientation();
		
		for (Point point :localPoints)
		{
			globalPoints.add(new Point(point.getX()+ mLocation.getX(), point.getY()+mLocation.getY()));
		}
		
		return globalPoints;
	}
	
	public List<Point> orientationGlobal(int orientation)
	{
		orientation = BlockShapeDefinitions.convertToValidOrientation(orientation);
		List<Point> globalPoints = new ArrayList<>();
		List<Point> localPoints = BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, orientation);
		
		for (Point point :localPoints)
		{
			globalPoints.add(new Point(point.getX()+ mLocation.getX(), point.getY()+mLocation.getY()));
		}
		
		return globalPoints;
	}
	
	public Point getLocation()
	{
		return mLocation;
	}
	
	public static Shape generateRandom() throws InvalidShapeException
	{
		Random random = new Random();
		return new Shape (random.nextInt(BlockShapeDefinitions.getNumShapes()));
	}
	
	
	
/*	public void printCurrentOrientation ()
	{
		GameField field = GameField.convertShapeToGameField(this, BlockShapeDefinitions.SHAPE_BLOCK_SIZE_X,BlockShapeDefinitions.SHAPE_BLOCK_SIZE_Y);
		GameField.printGameField (field);
	}
	
	public void printNextOrientation() throws InvalidShapeException
	{
		GameField field = GameField.convertShapeToGameField(getNextOrientation(), BlockShapeDefinitions.SHAPE_BLOCK_SIZE_X,BlockShapeDefinitions.SHAPE_BLOCK_SIZE_Y);
		
		
		GameField.printGameField (field);
	}
	
	public void printAllOrientationsForShape() throws InvalidShapeException
	{
		Shape tempShape = new Shape (mShapeType);
		for (int i = 0; i < 4; i++)
		{
			
			GameField field = GameField.convertShapeToGameField(tempShape, BlockShapeDefinitions.SHAPE_BLOCK_SIZE_X,BlockShapeDefinitions.SHAPE_BLOCK_SIZE_Y);
			GameField.printGameField (field);
			tempShape.rotateShape();
		}
	}*/
	
	private static Point initialPoint()
	{
		return new Point (3, Board.BOARD_SIZE_Y-4);
	}

	//abstract protected void putShapeInOrientation(int orientation);
	
	//abstract protected void empty();
	
//	abstract public void printInitialShape ();
//	abstract public void printCurrentOrientation ();
//	abstract public void printNextOrientation();
	
	
}

/* protected List<Point> mShape = new ArrayList<>(4);
 * private int ShapeType
 * private int Orientation
 * 
 * public List<Point> initialOrientation
 * public List<Point> nextOrientaion
 * public List<Point> currentOrientation
 * 
 * public Shape 
 *
*/