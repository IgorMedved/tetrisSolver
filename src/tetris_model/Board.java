package tetris_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ai.BoardHelper.GapCounter;
import exceptions.InvalidShapeException;
import tetris_model.contracts.TetrisContract;
import tetris_model.shapes.BlockShapeDefinitions;
import tetris_model.shapes.Shape;

// This class is used for representing main game board and also for showing nextShape
public class Board
{
	private static final List<Integer> EMPTYROW = initializeEmptyRow(); // empty row in main board. 
	//Using it speeds up board initialization
	private static final List<Integer> EMPTYNEXTROW = initializeEmptyNextRow(); // empty row in nextShape board
	
	protected List<List<Integer>> mBoard;
	private BoardType mBoardType;
	
	public Board (BoardType type)
	{
		mBoardType = type;
		initializeBoard ();
	}
	
	public Board(List<List<Integer>> board) {
		mBoard = new ArrayList<>(board.size());
		mBoardType =BoardType.MAINBOARD;
		ArrayList<Integer> innerList;
		for (int i = 0; i < board.size(); i++)
		{
			innerList = new ArrayList <>(board.get(i));
			mBoard.add(innerList);
		}
		
		
	}
	
	
	@Deprecated
	// This is a legacy function, but can still be used for debugging
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
	
	// use for debugging separately from user interface
	public synchronized String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = mBoard.size()-1; i >=0; i--)
		{
			for (int j = 0; j < mBoard.get(0).size(); j++)
			{
				
				
				if (mBoard.get(i).get(j)!=TetrisContract.EMPTY_SQUARE)
					sb.append("x");
				else
					sb.append("o");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	private void initializeBoard()
	{
		int sizeY;
		switch (mBoardType)
		{
		case MAINBOARD:
			sizeY = TetrisContract.BOARD_SIZE_Y;
			break;
		default:
			sizeY = TetrisContract.NEXT_SHAPE_BOARD_Y;
		}
		
		mBoard = new ArrayList<>(sizeY);
		
		for (int i = 0; i < sizeY ; i++)
		{
			if (mBoardType == BoardType.MAINBOARD)
				mBoard.add(new ArrayList<>(EMPTYROW));
			else
				mBoard.add(new ArrayList<>(EMPTYNEXTROW));
		}
		
	}
	
	
	
	
	public  List<Integer> deleteLines()
	{
		return deleteLines(0, mBoard.size()-1);
	}
	
	//check if any of the lines between rows lower and upper are completely filled and should be deleted
	public List<Integer> deleteLines(int lower, int upper)
	{
		List<Integer> deletedLines = new ArrayList<>();
		if (upper >= mBoard.size())
			return deletedLines;
		for (int row = upper; row >=lower; row--)
		{
			if (shouldDeleteLine(row))
			{
				deletedLines.add(row);
				removeRow(row);
			}
		}
		return deletedLines;
	}
	
	// delete line in @row
	public void removeRow (int row)
	{
		for (int i = row; i <mBoard.size()-1; i++)
			mBoard.set(i, mBoard.get(i+1));
		
		mBoard.set(mBoard.size()-1, new ArrayList<>(EMPTYROW));
	}
	
	// clear the board (for example at the start of the game)
	public void clear()
	{
		for (int i = 0; i <mBoard.size(); i++)
			if (mBoardType == BoardType.MAINBOARD)
				mBoard.set(i, new ArrayList<>(EMPTYROW));
			else
				mBoard.set(i, new ArrayList<>(EMPTYNEXTROW));
	}
	
	// checks whether the row is filled
	private boolean shouldDeleteLine (int row)
	{
		for (int col = 0; col <mBoard.get(0).size(); col++)
			if (mBoard.get(row).get(col)==TetrisContract.EMPTY_SQUARE)
				return false;
		
		return true;
	}

	// insert shape into the board, when it reached the bottom
	// unsafe insertion
	public void insert (List<Point> globalPoints, int shapeType)
	{
		for (Point point : globalPoints)
		{
			if (point.getY() >=18)
				System.out.println("Inserting shape " + shapeType + "\npoint: " + point.getX() + " " + point.getY() );
			else
				fillPoint(point, shapeType);
		}
	}
	
	@Deprecated
	// the function that used to remove the shape from the board to make the shape move
	// not needed anymore due to the implementation of how shape moves
	public void remove(List<Point> globalPoints)
	{
		for (Point point : globalPoints)
		{
			removePoint(point);
		}
	}
	
	// safe insertion checks if the shape can be inserted and is not out of bounds
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
	
	public boolean canInsert (Shape shape)
	{
		List<Point> globalPoints = shape.currentOrientationGlobal();
		if (shapeOutOfBounds(globalPoints))
			return false;
		else if (detectCollision (globalPoints))
			return false;
		
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
	
	@Deprecated
	// same as removeShape is not used in current implementation
	public void removePoint (int x, int y)
	{
		mBoard.get(y).set(x, TetrisContract.EMPTY_SQUARE);
	}
	
	@Deprecated
	// not used in current implementation
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
	
	
	
	public List<List<Integer>> getBoard()
	{
		return mBoard;
	}
	
	
	private static List<Integer> initializeEmptyRow()
	{
		List <Integer> emptyRow = new ArrayList<>(TetrisContract.BOARD_SIZE_X);
		for (int i = 0; i < TetrisContract.BOARD_SIZE_X; i++)
			emptyRow.add(TetrisContract.EMPTY_SQUARE);
		
		return emptyRow;
	}
	
	private static List<Integer> initializeEmptyNextRow()
	{
		List <Integer> emptyRow = new ArrayList<>(TetrisContract.NEXT_SHAPE_BOARD_X);
		for (int i = 0; i < TetrisContract.NEXT_SHAPE_BOARD_X; i++)
			emptyRow.add(TetrisContract.EMPTY_SQUARE);
		
		return emptyRow;
	}
}
