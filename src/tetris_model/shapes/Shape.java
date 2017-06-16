package tetris_model.shapes;


import java.util.ArrayList;

import java.util.List;
import java.util.Random;


import exceptions.InvalidShapeException;
import tetris_model.Board;
import tetris_model.Point;
import tetris_model.contracts.TetrisContract;

// Each shape can be represented by its shape type and orientation
// as well as location of the lower left corner of 5x4 insertion block
// By adding coordinates of the corner of the insertion block to coordinates 
// of the shape within the block as defined in BlockShapeDefinitions
// The global coordinates of the shape in the board can be easily calculated
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
			mLocation = location;
		}
	}
	
	
	public Shape (int shapeType) throws InvalidShapeException
	{
		this (shapeType, 0);
	}
	
	
	private Shape (Shape copy) throws InvalidShapeException 
	{
		this (copy.mShapeType, copy.mOrientation, new Point (copy.mLocation.getX(),copy.mLocation.getY()));
	}
	
	
	public static Shape shapeCopy(Shape copy)
	{
		try {
			return new Shape(copy);
		} catch (InvalidShapeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Shape shapeCopyInOrientation(Shape copy, int desiredOrientation)
	{
		try {
			return new Shape (copy.mShapeType, desiredOrientation, new Point (copy.mLocation.getX(), copy.mLocation.getY()));
		} catch (InvalidShapeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// returns true if the shape would take a valid position after moving 1 square to the left of current location
	// the function does not modify current shape
	public boolean canMoveLeft(Board board)
	{
		return board.canInsert(left());
	}
	
	// returns true if the shape would assume a valid position after moving 1 square to the right of current location
	// the function does not modify current shape
	public boolean canMoveRight(Board board)
	{
		return board.canInsert(right());
	}
	
	// returns true if the shape would assume a valid position after moving 1 square down of current location
	// the function does not modify current shape
	public boolean canMoveDown(Board board)
	{
		return board.canInsert(down());
	}
	
	// returns true if the shape would assume a valid position after rotating 90 degrees clockwise
	// the function does not modify current shape
	public boolean canRotate(Board board)
	{
		return board.canInsert(getNextOrientation());
	}
	
	// rotate shape 90 degrees clockwise
	public void rotateShape ()
	{
		mOrientation = BlockShapeDefinitions.getNextOrientation(mOrientation);
	}
	
	// move shape 1 square left of the current position
	public void moveShapeLeft ()
	{
		mLocation = mLocation.left();
	}
	
	// move shape 1 square to the right of the current position
	public void moveShapeRight()
	{
		mLocation = mLocation.right();
		
	}
	
	// move shape 1 square down of the current position
	public void moveShapeDown()
	{
		mLocation = mLocation.down();
	}
	
	// returns new shape of the same type and in the same orientation as the original shape, but a the new location
	public Shape movedShape (Point newLocation)
	{
		try
		{
			return new Shape(mShapeType, mOrientation, newLocation);
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// returns new shape of the same type and in the same orientation as the original shape, but one square to the left
	// of current location. The shape is not guaranteed to be insertable into the board
	public Shape left()
	{
		return movedShape (mLocation.left());
	}
	

	// returns new shape of the same type and in the same orientation as the original shape, but one square to the right
	// of current location. The shape is not guaranteed to be insertable into the board
	public Shape right()
	{
		return movedShape (mLocation.right());
	}
	
	// returns new shape of the same type and in the same orientation as the original shape, but one square down
	// of current location. The shape is not guaranteed to be insertable into the board
	public Shape down()
	{
		return movedShape (mLocation.down());
	}
	
	
	// Get the shape that would be produced by rotating current shape 90 degrees clockwise
	public Shape getNextOrientation() 
	{
		try
		{
			return new Shape (mShapeType, BlockShapeDefinitions.getNextOrientation(mOrientation), mLocation);
		} catch (InvalidShapeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;// this method always returns valid shape
	}
	
	
	// represent the shape as collection of points within the 5x4 insertion block in initial orientation	
	public List<Point> initialOrientation ()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, 0);
	}
	
	// represent the shape as collection of points within the 5x4 insertion block in current orientation
	private List<Point> currentOrientation()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, mOrientation);
	}
	
	// get the collection of points that would represent current shape rotated by 90 degrees clockwise within the insertion block
	private List<Point> nextOrientation()
	{
		return BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, BlockShapeDefinitions.getNextOrientation(mOrientation));
	}
	

	// convert the collection of points representing the shape within insertion block to global points
	private List<Point> getGlobalPoints(List<Point> localPoints)
	{
		List<Point> globalPoints = new ArrayList<>();
		for (Point point :localPoints)
		{
			globalPoints.add(new Point(point.getX()+ mLocation.getX(), point.getY()+mLocation.getY()));
		}
		
		return globalPoints;
	}
	
	// represent current shape as a collection of global points in initial orientation
	public List<Point> initialOrienationGlobal()
	{
		return getGlobalPoints (initialOrientation());
	}
	
	// represent current shape as a collection of global points
	public List<Point> currentOrientationGlobal()
	{
		return getGlobalPoints(currentOrientation());
	}
	
	// get a collection of global points representing a rotation of the current shape by 90 degrees clockwise
	public List<Point> nextShapeGlobal ()
	{
		List <Point> originalPoints = initialOrientation();
		List <Point> globalPoints = new ArrayList<>(originalPoints.size());
		// substract 1 from y coordinate of points returned by shape.initialOrientation();
		// unless shape is a line then also substract 1 from x cordinate
		for (Point point: originalPoints)
		{
			if (mShapeType== TetrisContract.LINE_SHAPE)
				globalPoints.add(new Point(point.getX()-1, point.getY()-1));
			else
				globalPoints.add (new Point (point.getX(), point.getY()-1));
		}
		return globalPoints;
	}
	
	// get  a collection of global points representing locations of hint arrows that show where the shape should move
	// based on ai calculations
	public List<Point> getHintsLocations()
	{
		List<Point> hints = new ArrayList<>(3); // there are 4 arrows left, right, rotate, and drop_down
		int leftX = getLeftEdge()-1; // the left arrow should be located one square to the left of the shape's left edge
		int leftY = getBottomEdge();
		int turnX = leftX < 0? 0: leftX; // the arrow showing rotations should be one square above the top edge and  
		int turnY = getTopEdge()+1; // one square to the left of the left edge of the shape 
		int rightX = getRightEdge()+1; // right arrow should be to the right of the right edge
		int rightY = leftY;
		
		int downX = (leftX+ rightX)/2; // down arrow should below the bottom edge 
		int downY =leftY<=0? 0: leftY-1;
		turnY = turnY==TetrisContract.BOARD_SIZE_Y?turnY-1:turnY;
		
		hints.add(new Point(leftX, leftY)); // location of left arrow;
		hints.add(new Point(turnX, turnY));// location of turn arrow;
		hints.add(new Point(rightX, rightY));// location of right arrow;
		hints.add(new Point(downX, downY)); // location of down arrow
		
		
		return hints;
	}
	
	// get the leftmost column that the shape occupies
	public int getLeftEdge()
	{
		return AvailableMovements.getShapeBorder(mShapeType, mOrientation).xLeft + mLocation.getX();
	}
	
	// get the rightmost column that the shape occupies
	public int getRightEdge()
	{
		return AvailableMovements.getShapeBorder(mShapeType, mOrientation).xRight + mLocation.getX();
	}
	
	// get the lowest row that the shape occupies
	public int getBottomEdge()
	{
		return AvailableMovements.getShapeBorder(mShapeType, mOrientation).yDown + mLocation.getY();
	}
	
	// get the top row that the shape occupies
	public int getTopEdge()
	{
		return AvailableMovements.getShapeBorder(mShapeType, mOrientation).yUp + mLocation.getY();
	}
	
	
	// get the top row for the @col that the shape occupies
	public int getTopRow (int col)
	{
		return AvailableMovements.getHeights(mShapeType, mOrientation, col).getY() + mLocation.getY();
	}
	
	// get the yCoordinate of the lowest point in the left edge of the shape
	public int getBottomLeft()
	{
		int leftCol = AvailableMovements.getShapeBorder(mShapeType, mOrientation).xLeft;
		return AvailableMovements.getHeights(mShapeType, mOrientation, leftCol).getX() + mLocation.getY();
	}
	
	// the the yCoordinate of the lowest point in the right edge of the shape
	public int getBottomRight()
	{
		int rightCol = AvailableMovements.getShapeBorder(mShapeType, mOrientation).xRight;
		return AvailableMovements.getHeights(mShapeType, mOrientation, rightCol).getX() + mLocation.getY();
	}
	
	public int [] lowestPointCoordinates()
	{
		//List<Integer> lowestPoint = new ArrayList
		Point[] shapeInOrientation = BlockShapeDefinitions.getShapeInOrientation(mShapeType, mOrientation);
		int [] lowestPointCoordinates = new int[5];
		int length = 0;
		int height = shapeInOrientation[0].getY();
		do
		{
			length++;
			lowestPointCoordinates[length] = shapeInOrientation[length-1].getX()+mLocation.getX();
		}
		while(length < 4 && shapeInOrientation[length].getY() == height);
		lowestPointCoordinates[0]= length;
		return lowestPointCoordinates;
	}
	
	
	public List<Point> nextOrientationGlobal()
	{
		return getGlobalPoints (nextOrientation());
	}
	
	
	public List<Point> orientationGlobal(int orientation)
	{
		orientation = BlockShapeDefinitions.convertToValidOrientation(orientation);
		return getGlobalPoints (BlockShapeDefinitions.getShapeDefinitionInOrientation(mShapeType, orientation));
	}
	

	public Point getLocation()
	{
		return mLocation;
	}
	
	
	public void setLocation (Point p)
	{
		mLocation.setPoint(p.getX(), p.getY());
	}
	
	
	public static Shape generateRandom() throws InvalidShapeException
	{
		Random random = new Random();
		return new Shape (random.nextInt(BlockShapeDefinitions.getNumShapes()));
	}
	
	
	public int getNumRotations()
	{
		return BlockShapeDefinitions.getNumRotations(mShapeType);
	}
	
	
	// generate the initial point for the lower left corner of the insertion block
	private static Point initialPoint()
	{
		return new Point (TetrisContract.INITIAL_POINT.getX(), TetrisContract.INITIAL_POINT.getY());
	}

	
	public int getOrientation()
	{
		return mOrientation;
	}

	
	public int getShapeType()
	{
		return mShapeType;
	}

	
	public void setOrientation(int orientation)
	{
		mOrientation = orientation;
	}


	public void setShapeType(int shapeType)
	{
		mShapeType = shapeType;
	}
	
	public void printShape()
	{
		System.out.println("Shape: " + mShapeType + " rotations " + mOrientation + " inserted at " + mLocation.getX() + " " + mLocation.getY());
	}
}