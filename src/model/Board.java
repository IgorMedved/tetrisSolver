package model;

import java.util.ArrayList;
import java.util.Arrays;
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
		initializeBoard (BOARD_SIZE_X, BOARD_SIZE_Y );
	}
	
	public synchronized String convertBoardToString()
	{
		StringBuilder sb = new StringBuilder("<html>");
		for (int i = mBoard.size()-1; i >=0; i--)
		{
			for (int j = 0; j < mBoard.get(0).size(); j++)
			{
				
				
				if (mBoard.get(i).get(j))
					sb.append("x");
				else
					sb.append("o");
			}
			
			sb.append("<br>");
		}
		
		return sb.toString();
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
				if (i == 0)
				{
					xRow.add(FILLED);
				}
				else
				xRow.add(EMPTY);
				
			}
			mBoard.add(xRow);
		}
		
	}
	
	
	public  List<Integer> deleteLines()
	{
		List<Integer> deleteLines = new ArrayList<>();
		for (int row = BOARD_SIZE_Y-1; row >=0; row--)
			if (shouldDeleteLine(row))
			{
				deleteLines.add(row);
				removeRow(row);
			}
		
		
		return deleteLines;
			
	}
	
	public void removeRow (int row)
	{
		for (int i = row; i <BOARD_SIZE_Y-1; i++)
			for (int j = 0; j< BOARD_SIZE_X; j++)
			{
				mBoard.get(i).set(j, mBoard.get(i+1).get(j));
			}
		
		for (int i = 0; i< BOARD_SIZE_X; i++)
			mBoard.get(BOARD_SIZE_Y-1).set(i, EMPTY);
	}
	
	private boolean shouldDeleteLine (int row)
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
		int counter = 1;
		for (Point point: globalPoints)
		{
			
			if (mBoard.get(point.getY()).get(point.getX()))
				return true;
		}
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
	
	public List<Boolean> emptyRow()
	{
		return Arrays.asList(new Boolean[BOARD_SIZE_X]);
	}
}
