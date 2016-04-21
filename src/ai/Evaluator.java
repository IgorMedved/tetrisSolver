package ai;

import java.util.List;

import tetris_model.Board;
import tetris_model.contracts.TetrisContract;

public class Evaluator extends Board
{

	public int evaluateFor (List<List<Integer>> board, int shapeType )
	{
		return 0;
	}
	
	public int numHolesInColumn (List<List<Integer>> board, int col)
	{
		int counter = 0;
		boolean blockAbove = false;
		
		for (int row = Board.BOARD_SIZE_Y -1; row>=0; row--)
		{
			if (!blockAbove && board.get(row).get(col)!=TetrisContract.EMPTY_SQUARE)
				blockAbove = true;
			else if (blockAbove && board.get(row).get(col)==TetrisContract.EMPTY_SQUARE )
				counter ++;
		}
		
		return counter;
	}
	
	// returns the height of specified column
	public int columnHeight (List<List<Integer>> board, int col)
	{
		for (int row = Board.BOARD_SIZE_Y-1; row >=0; row --)
			if (board.get(row).get(col) != TetrisContract.EMPTY_SQUARE)
				return row;
		return -1;
	}
	
	
	// returns the index and height of the highest column. If more than one column has the same top height returns leftmost column and it's index
	public Pair topNonEmpty (List<List<Integer>> board)
	{
		int largest = 0;
		int index = 0;
		for (int col = 0; col < Board.BOARD_SIZE_X; col++)
		{
			int temp = columnHeight(board, col);
			if (largest < temp)
			{
				largest = temp;
				index = col;
			}
			
			
		}
		
		return new Pair(index, largest);
	}
		
}
