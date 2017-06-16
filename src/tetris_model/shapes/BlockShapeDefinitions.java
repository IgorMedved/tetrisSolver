package tetris_model.shapes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tetris_model.Point;



public class BlockShapeDefinitions
{
	// shapeType constants for easy identification of each shape
	public static final int S_SHAPE = 0;
	public static final int INVERTED_S_SHAPE = 1;
	public static final int LINE_SHAPE = 2;
	public static final int T_SHAPE = 3;
	public static final int L_SHAPE = 4;
	public static final int INVERTED_L_SHAPE = 5;
	public static final int SQUARE_SHAPE = 6;
	
	// total number of shapes
	private static final int NUM_SHAPES = 7;
	// each shape can be in 4 orientations which are rotation invariants of each other
	private static final int NUM_ORIENTATIONS = 4;
	
	// size of the shape insertion block
	public static final int SHAPE_BLOCK_SIZE_X =5;
	public static final int SHAPE_BLOCK_SIZE_Y =4;
	
	
	public static int getNumShapes()
	{
		return NUM_SHAPES;
	}
	
	// numRotations represents the number of distinguishable orientations for each shape
	// for example it is one for square, 2 for s-shape and line, and 4 for t-shape and l-shape
	public static int getNumRotations(int shapeType)
	{
		switch (shapeType)
		{
		case S_SHAPE:
		case INVERTED_S_SHAPE:
		case LINE_SHAPE:
			return 2;
		case T_SHAPE:
		case L_SHAPE:
		case INVERTED_L_SHAPE:
			return 4;
		default: //square
			return 1;
		
		}
	}
	
	public static int getNumOrientations()
	{
		return NUM_ORIENTATIONS;
	}
	
	// There are NUM_SHAPES different shape types as defined above
	public static boolean isValidShapeType(int shapeType)
	{
		return shapeType >=0 && shapeType < NUM_SHAPES;
	}
	
	// each shape can be in one of 4 orientations that are achieved by rotating a shape clockwise
	public static boolean isValidOrientation (int orientation)
	{
		return orientation >=0 && orientation < NUM_ORIENTATIONS;
	}
	
	// orientation can be an integer between 0 and 3. Rotating a shape one more time in orientation 3 brings it back to orientation 0
	public static int convertToValidOrientation (int orientation)
	{
		if (orientation > 0)
			orientation %=4;
		else if (orientation <0)
			orientation = (4*(-1*orientation/4 + 1)+orientation)%4;
			
			return orientation;
	}
	
	// function returns a next orientation 
	public static int getNextOrientation (int orientation)
	{
		return convertToValidOrientation(orientation + 1);
	}
	
	public static int getPreviousOrientation (int orientation)
	{
		return convertToValidOrientation(orientation -1);
	}
	
	// get a representation of a tetris figure (@shapeType) as a list of 4 points inside a 5x4 block for a particular orientation
	public static List<Point> getShapeDefinitionInOrientation(int shapeType, int orientation )
	{
		if (isValidShapeType(shapeType) && isValidOrientation (orientation))
			return Arrays.asList(ALL_SHAPE_DEF[shapeType][orientation]);
			
			return null;
			
	}
	
	// return representations of a tetris figure (@shapeType) as a list of 4 points inside a 5x4 block for all 4 orienations it can be in
	public static List<List<Point>> getShapeDefinitionForShapeType(int shapeType)
	{
		if (!isValidShapeType(shapeType))
			return null;
		
		
		List<List<Point>> shapeDefinition = new ArrayList<>(NUM_ORIENTATIONS);
		for (int i = 0; i <NUM_ORIENTATIONS; i++ )
		{
			shapeDefinition.add(getShapeDefinitionInOrientation(shapeType, i));
		}
		
		return shapeDefinition;
		
		
		
	}
		
		
		
	// tetris shape definitions as Arrays of 4 points
	// Do not change! Other classes rely on the exact order in which each point within an orientation is defined
	// going left to right from lowest row to highest row
	private final static Point[][] S_SHAPE_DEF = new Point[][]{{new Point (1,1), new Point (2,1), new Point (2,2), new Point (3,2)},
																{new Point (3,1), new Point (2,2), new Point (3,2), new Point (2,3)},
																{new Point (1,2), new Point (2,2), new Point (2,3), new Point (3,3)},
																{new Point (2,1), new Point (1,2), new Point (2,2), new Point (1,3)}};
	
	private final static Point[][] INVERTED_S_SHAPE_DEF = new Point[][]{{new Point (2,1), new Point (3,1), new Point (1,2), new Point (2,2)},
																{new Point (2,1), new Point (2,2), new Point (3,2), new Point (3,3)},
																{new Point (2,2), new Point (3,2), new Point (1,3), new Point (2,3)},
																{new Point (1,1), new Point (1,2), new Point (2,2), new Point (2,3)}};
																
	private final static Point[][] LINE_SHAPE_DEF = new Point[][]{{new Point (1,2), new Point (2,2), new Point (3,2), new Point (4,2)},
																	{new Point (2,0), new Point (2,1), new Point (2,2), new Point (2,3)},
																	{new Point (0,1), new Point (1,1), new Point (2,1), new Point (3,1)},
																	{new Point (2,0), new Point (2,1), new Point (2,2), new Point (2,3)}};
																	
	private final static Point[][] T_SHAPE_DEF = new Point[][]{{new Point(2,1), new Point(1,2), new Point(2,2), new Point(3,2)},
    															{new Point(2,1), new Point(1,2),new Point(2,2), new Point(2,3)},
    															{new Point(1,2), new Point(2,2), new Point(3,2), new Point(2,3)},
    															{new Point(2,1), new Point(2,2), new Point(3,2), new Point(2,3)}};
    															
    private final static Point[][] L_SHAPE_DEF = new Point[][]{{new Point (1,1), new Point (1,2), new Point(2,2), new Point(3,2)},
    															{new Point (2,1), new Point (2,2), new Point(1,3), new Point(2,3)},
    															{new Point (1,2), new Point (2,2), new Point(3,2), new Point(3,3)},
    															{new Point (2,1), new Point (3,1), new Point(2,2), new Point(2,3)}};
    
    private final static Point[][] INVETED_L_SHAPE_DEF = new Point[][]{{new Point (3,1),new Point (1,2), new Point(2,2), new Point(3,2)},
    																	{new Point (1,1), new Point(2,1), new Point(2,2), new Point(2,3)},
    																	{new Point (1,2), new Point(2,2), new Point(3,2), new Point(1,3)},
    																	{new Point (2,1), new Point(2,2), new Point(2,3), new Point(3,3)}};
    															
																	
	private final static Point[][] SQUARE_SHAPE_DEF = new Point[][]{{new Point(2,1), new Point(3,1), new Point(2,2), new Point(3,2)},
																	{new Point(2,0), new Point(3,0), new Point(2,1), new Point(3,1)},
																	{new Point(1,0), new Point(2,0), new Point(1,1), new Point(2,1)},
																	{new Point(1,1), new Point(2,1), new Point(1,2), new Point(2,2)}};
																	
																	
																	
    
																
																
	
	private final static Point[][][] ALL_SHAPE_DEF = new Point[][][]{S_SHAPE_DEF, INVERTED_S_SHAPE_DEF, LINE_SHAPE_DEF, 
																		T_SHAPE_DEF, L_SHAPE_DEF, INVETED_L_SHAPE_DEF, SQUARE_SHAPE_DEF};
																		
	public static Point[] getShapeInOrientation(int shape, int orientation)
	{
		return ALL_SHAPE_DEF[shape][orientation];
	}

}

