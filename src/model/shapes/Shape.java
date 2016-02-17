package model.shapes;


import java.util.List;
import java.util.Random;

import model.GameField;
import model.Point;
import exceptions.InvalidShapeException;

public class Shape
{
	
	private int mOrientation;
	private int mShapeType;
	
	// create shape in particular orientation
	// if shapeType is not valid throw Invalid Shape Exception
	public Shape (int shapeType, int orientation) throws InvalidShapeException
	{
		if (!BlockShapeDefinitions.isValidShapeType(shapeType))
			throw new InvalidShapeException(shapeType);
		
		else
		{
			mShapeType = shapeType;
			mOrientation = BlockShapeDefinitions.convertToValidOrientation(orientation);
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
	
	public List<Point> initialOrienation ()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, 0);
	}
	
	public List<Point> currentOrientation()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, mOrientation);
	}
	
	public List<Point> nextOrientation()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, BlockShapeDefinitions.getNextOrientation(mOrientation));
	}
	
	public static Shape generateRandom() throws InvalidShapeException
	{
		Random random = new Random();
		return new Shape (random.nextInt(BlockShapeDefinitions.getNumShapes()));
	}
	
	
	
	public void printCurrentOrientation ()
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