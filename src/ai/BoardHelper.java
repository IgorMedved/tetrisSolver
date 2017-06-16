package ai;

import java.util.ArrayList;
import java.util.List;

import tetris_model.contracts.TetrisContract;
import tetris_model.shapes.Shape;

// Board helper class is used to find two main characteristics of the tetris board
// 1) height of each column in the board
// 2) number of gaps for each row in the board
// Gap is defined as empty square in a column that has at least one filled square above it
// The class is used by the AI evaluator functions to speed up board position evaluation
public class BoardHelper {
	// array that stores the heights of each column in the board
	private List <Integer> heights = new ArrayList<>(TetrisContract.BOARD_SIZE_X);
	// each gap counter has information on gaps in one row of the board
	private List <GapCounter> gapCounter = new ArrayList<>(TetrisContract.BOARD_SIZE_Y-1);
	private int maxHeight; // maximum column height in a board
	private int minHeight; 
	
	// main constructor
	public BoardHelper (List<List<Integer>> board, boolean createEnumerator)
	{
		createHeights(board);
		if (createEnumerator)
			createCounter(board);
	}
	
	// copy constructor
	public BoardHelper (BoardHelper original)
	{
		if (original!= null)
		{
			heights = new ArrayList<>(original.heights);
			if (original.gapCounter!=null)
				gapCounter = copyGapCounter(original.gapCounter);
			maxHeight = original.maxHeight;
			minHeight = original.minHeight;
		}
	}
	
	// function to efficiently copy contents of gapCounter
	public List<GapCounter> copyGapCounter(List<GapCounter> original)
	{
		List<GapCounter> copy = new ArrayList<>(original.size());
		for (GapCounter gapCounter:original)
		{
			copy.add(new GapCounter (gapCounter));
		}
		return copy;
	}
	
	// using the board create list of col heights
	// this function take O(NxM) where N - is number of rows and M - number of columns
	public void createHeights(List<List<Integer>> board)
	{
		boolean allEmpty;
		maxHeight = -1;
		minHeight = board.size() -1;
		int j;
		int i;
		for (j = 0 ; j  < board.get(0).size(); j ++)
		{
			allEmpty = true;
			for (i = board.size()-1; i >=0; i--)
			{
				if (board.get(i).get(j) != TetrisContract.EMPTY_SQUARE)
				{
					
					heights.add(i);
					if (minHeight > i)
						minHeight = i;
					if (maxHeight < i)
						maxHeight = i;
					allEmpty = false;
					break;
				}
				
					
			}
			if (allEmpty)
			{
				heights.add(-1);
				minHeight=-1;
			}
		}
	}
	
	// this function creates GapCounter list for each row of the board
	// It is O(NXM) in the worst case,  where N - is number of rows and M - number of columns of the board
	// however if the board is not filled it is as quick as O(N+M);
	public void createCounter (List<List<Integer>> board)
	{
		int i, j;
		
		for (i = board.size()-2; i >=0; i--)
			gapCounter.add(new GapCounter());
		
		for (j = 0; j< heights.size(); j++ )
			for (i = heights.get(j)-1; i>=0; i--)
				if (board.get(i).get(j) == TetrisContract.EMPTY_SQUARE)
				{
					List <Integer> empties =  gapCounter.get(gapCounter.size()-1 -i).numEmpties;
					empties.add(j);
					if (empties.size() >1 && empties.get(empties.size()-2) > j+1)
						gapCounter.get(gapCounter.size()-1-i).twoSeparateEmptiesInRow = true;
				}
	}
	
	
	// as described above creating new list of heights for the board takes as much O(NxM) 
	//  where N - is number of rows and M - number of columns
	// It is much faster to update the list of heights using the information on the number of Lines
	// deleted in the previous turn and the previous shape dropped 
	// updateHeights is O(4xN + M) in the worst case, but can be as fast as O(1)
	public void updateHeights(List<List<Integer>> board, Shape previousShape, int linesDeleted)
	{
		boolean updateMinHeight = false;
		
		// update height for the effect of the new inserted shape
		for (int i = previousShape.getLeftEdge(); i<= previousShape.getRightEdge(); i++)
		{
			heights.set(i, previousShape.getTopRow(i- previousShape.getLocation().getX()));
		}
		
		maxHeight = Math.max(maxHeight, previousShape.getTopEdge());
		
		
		// update heights due to line deletion (both decrease by lines deleted and possible uncovering of gaps
		if (linesDeleted >0)
		{	
			maxHeight -= linesDeleted;
			minHeight = Integer.MAX_VALUE;
			for (int i = 0; i < heights.size(); i++)
			{
				boolean allEmpty = true;
				for (int j = heights.get(i)-linesDeleted; j >= 0; j-- )
					if (board.get(j).get(i) != TetrisContract.EMPTY_SQUARE)
					{
						allEmpty = false;
						heights.set(i, j);
						if (minHeight > j)
							minHeight = j;
						
						break;
							
					}
				if (allEmpty)
				{
					heights.set(i,-1);
					minHeight = -1;
				}
			}
		}
		else if (updateMinHeight) // if no lines are deleted the minHeight needs to be updated separately
		{
			updateMinHeight();
		}
		
	}
	
	
	// update minimum Column height
	public void updateMinHeight()
	{
		minHeight = Integer.MAX_VALUE;
		for (int height: heights)
			if (height < minHeight)
				minHeight = height;
	}
	
	public int getMaxHeight()
	{
		return maxHeight;
	}
	
	public int getMaxHeightDifference()
	{
		return maxHeight - minHeight;
	}
	
	public class GapCounter
	{
		public boolean twoSeparateEmptiesInRow;
		public List <Integer> numEmpties; // list of columns that has gaps for a given row
		
		public GapCounter ()
		{
			twoSeparateEmptiesInRow = false;
			numEmpties = new ArrayList<>(TetrisContract.BOARD_SIZE_X-1);
		}
		
		public GapCounter (GapCounter original)
		{
			twoSeparateEmptiesInRow = original.twoSeparateEmptiesInRow;
			numEmpties = new ArrayList<>(original.numEmpties);
		}
		
	}
	
	public List<Integer> getHeights()
	{
		return heights;
	}
	
	public List<GapCounter> getGaps ()
	{
		return gapCounter;
	}
	
	public void printHeights()
	{
		System.out.println("The heights of the board are ");
		for (Integer height: heights)
			System.out.print(height + " ");
		System.out.println();
	}
}
