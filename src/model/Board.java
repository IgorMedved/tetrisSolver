package model;

import java.util.ArrayList;
import java.util.List;

import model.shapes.Shape;

public class Board
{
	public static final boolean FILLED = true;
	public static final boolean EMPTY = false;
	
	public static final int BOARD_SIZE_X = 12;
	public static final int BOARD_SIZE_Y = 18;
	
	private List<List<Boolean>> mBoard;
	
	public Board ()
	{
		initializeBoard (BOARD_SIZE_X, BOARD_SIZE_X );
	}
	
	private void initializeBoard(int sizeX, int sizeY)
	{
		mBoard = new ArrayList<>();
		List<Boolean> xRow;
		
		for (int i = 0; i < sizeY ; i++)
		{
			xRow = new ArrayList<>();
			for (int j = 0; j < sizeX; j ++ )
			{
				xRow.add(EMPTY);
				
			}
			mBoard.add(xRow);
		}
		
	}
	
	
	public boolean  deleteLines()
	{
		boolean linesDeleted =false;
		for (int row = 0; row <BOARD_SIZE_Y; row++)
			linesDeleted = deleteLine(row);
		
		if (linesDeleted)
			notify();
		
		return linesDeleted;
			
	}
	
	private boolean deleteLine (int row)
	{
		for (int col = 0; col <BOARD_SIZE_X; col++)
			if (!mBoard.get(row).get(col))
				return false;
		
		return true;
	}

	public void insert (List<Point> globalPoints)
	{
		for (Point point : globalPoints)
		{
			fillPoint(point);
		}
	}
	
	public void remove(List<Point> globalPoints)
	{
		for (Point point : globalPoints)
		{
			
			removePoint(point);
		}
	}
	
	public boolean insertShapeIntoBoard (Shape shape)
	{
		List<Point> globalPoints = shape.currentOrientationGlobal();
		
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
			if (mBoard.get(point.getY()).get(point.getX()))
				return true;
		return false;
	}
	
	public void fillPoint (int x, int y)
	{
		mBoard.get(y).set(x,FILLED);
	}
	
	public void fillPoint (Point point)
	{
		fillPoint(point.getX(), point.getY());
	}
	public void removePoint (int x, int y)
	{
		mBoard.get(y).set(x, EMPTY);
	}
	
	public void removePoint (Point point)
	{
		removePoint (point.getX(), point.getY());
	}
	
	public int getHeight()
	{
		return mBoard.size();
	}
	
	public int getWidth()
	{
		return mBoard.get(0).size();
	}
}
