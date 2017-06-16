package tetris_model.shapes;

import java.util.ArrayList;
import java.util.List;

import tetris_model.Board;
import tetris_model.Point;
import tetris_model.contracts.TetrisContract;


// this class is used to optimize movement of the shape within the board
public class AvailableMovements {
	
	private static Borders[][] borders = initializeBorders(); // borders of the shape inside the insertion block
	private static Point[][][] heightsForX = initializeHeights(); // lower and upper bounds inside the insertion block for the shape
																// given the xCoordinate within the insertion block
	
	
	private static Borders[][] initializeBorders	()	
	{
		Borders[][] borders = new Borders[BlockShapeDefinitions.getNumShapes()][BlockShapeDefinitions.getNumOrientations()];
		for (int shape = 0; shape < borders.length; shape++)
			for (int orientation = 0; orientation < borders[shape].length; orientation++)
			{
				Point[] shapeInOrientation = BlockShapeDefinitions.getShapeInOrientation(shape, orientation);
				int yMin = shapeInOrientation[0].getY();
				int xLeft = shapeInOrientation[0].getX();
				int xRight = shapeInOrientation[0].getX();
				int yMax = shapeInOrientation[0].getY();
				for (Point point:shapeInOrientation)
				{
					if (point.getY() < yMin)
						yMin = point.getY();
					else if (point.getY()> yMax)
						yMax = point.getY();
					if (point.getX() < xLeft)
						xLeft = point.getX();
					else if (point.getX() > xRight)
						xRight = point.getX();
				}
				borders[shape][orientation] = new Borders (yMin, yMax, xLeft, xRight);
			}
		
		return borders;
	}
	
	private static Point[][][] initializeHeights ()
	{
		Point [][][] heightForX = new Point[BlockShapeDefinitions.getNumShapes()][BlockShapeDefinitions.getNumOrientations()][5];
		for (int shape = 0; shape < borders.length; shape++)
			for (int orientation = 0; orientation < borders[shape].length; orientation++)
			{
				Point[] shapeInOrientation = BlockShapeDefinitions.getShapeInOrientation(shape, orientation);
				for (int x = borders[shape][orientation].xLeft; x <= borders[shape][orientation].xRight; x++)
				{
					int minY = Integer.MAX_VALUE;
					int maxY = Integer.MIN_VALUE;
					for (Point point:shapeInOrientation)
					{
						if (point.getX() == x && point.getY() < minY)
							minY = point.getY();
						if (point.getX()== x && point.getY() > maxY)
							maxY = point.getY();
					}
					heightForX[shape][orientation][x] = new Point (minY, maxY);
				}
					
			}
		
		return heightForX;
	}
	
	
	public static Borders getShapeBorder (int shape, int orientation)
	{
		return borders[shape][orientation];
	}
	
	// returns the leftMost  x-coordinate of the shape insertion block in global coordinates
	public static int minLeft (int shape, int orientation)
	{
		return 0 - borders[shape][orientation].xLeft;
	}
	
	public static Point getHeights (int shape, int rotation, int col)
	{
		return heightsForX[shape][rotation][col];
	}
	
	// returns the rightMost x-coordinate of the shape insertion block in global coordinates
	public static int maxRight (int shape, int orientation)
	{
		return TetrisContract.BOARD_SIZE_X - 1 - borders[shape][orientation].xRight; 
	}
	
	
	// returns y-coordinate of the shape insertion block for a given xCoordinate in global coordinates
	// xCoordinate - is the leftMost row that the shape takes in global coordinates
	public static int maxDown (int shape, int orientation, int xCoordinate, List <Integer> heights)
	{
		int maxY = Integer.MIN_VALUE;
		for (int x = borders[shape][orientation].xLeft; x <= borders[shape][orientation].xRight; x++)
		{
			if (heights.get(xCoordinate + x - borders[shape][orientation].xLeft)+1 - heightsForX[shape][orientation][x].getX() > maxY)
				maxY =heights.get(xCoordinate+ x - borders[shape][orientation].xLeft)+1 - heightsForX[shape][orientation][x].getX();
		}
		return maxY;
	}
	
	
	
	// used for debugging
	public static void printBorders()
	{
		for (int i = 0; i < borders.length; i++)
			for (int rotation = 0; rotation < borders[0].length; rotation ++  )
			{
				System.out.println("Shape: " + i + " rotation: " + rotation);
				System.out.println(borders[i][rotation]);
			}
				
	}
	
	// used for debugging
	public static void printLowerBorders ()
	{
		for (int i = 0; i < borders.length; i ++)
		{
			System.out.println("Shape: " + i);
			for (int j = 0; j < borders[0].length; j++)
			{
				System.out.print("rotation: " + j + "\t\t");
				for (int k = 0; k <5; k++)
					if (heightsForX[i][j][k] != null)
						System.out.print(heightsForX[i][j][k].getX() + " " );
				
				System.out.println();
			}
						
		}
	}
}



