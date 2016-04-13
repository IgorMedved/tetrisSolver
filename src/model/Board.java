package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.contracts.TetrisContract;
import model.shapes.Shape;

public class Board
{
	//public static final boolean FILLED = true;
	//public static final boolean EMPTY = false;
	
	
	
	
	
	public static final int BOARD_SIZE_X = 12;
	public static final int BOARD_SIZE_Y = 18;
	
	private List<List<Integer>> mBoard;
	
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
				
				
				if (mBoard.get(i).get(j)!=TetrisContract.EMPTY_SQUARE)
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
		List<Integer> xRow;
		
		for (int i = 0; i < sizeY ; i++)
		{
			xRow = new ArrayList<>();
			for (int j = 0; j < sizeX; j ++ )
			{
				
				xRow.add(TetrisContract.EMPTY_SQUARE);
				
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
			mBoard.get(BOARD_SIZE_Y-1).set(i, TetrisContract.EMPTY_SQUARE);
	}
	
	private boolean shouldDeleteLine (int row)
	{
		for (int col = 0; col <BOARD_SIZE_X; col++)
			if (mBoard.get(row).get(col)==TetrisContract.EMPTY_SQUARE)
				return false;
		
		return true;
	}

	public void insert (List<Point> globalPoints, int shapeType)
	{
		for (Point point : globalPoints)
		{
			fillPoint(point, shapeType);
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
		
		insert(globalPoints, shape.getShapeType());
		
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
			
			if (mBoard.get(point.getY()).get(point.getX())!=TetrisContract.EMPTY_SQUARE)
				return true;
		}
		return false;
	}
	
	public void fillPoint (int x, int y, int pointType)
	{
		mBoard.get(y).set(x,pointType);
	}
	
	public void fillPoint (Point point, int pointType)
	{
		fillPoint(point.getX(), point.getY(), pointType);
	}
	public void removePoint (int x, int y)
	{
		mBoard.get(y).set(x, TetrisContract.EMPTY_SQUARE);
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
