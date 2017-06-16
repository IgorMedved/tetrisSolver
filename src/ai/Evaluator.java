package ai;

import tetris_model.Board;
import tetris_model.contracts.TetrisContract;

// This class provides a set of functions for evaluating a given board position
public class Evaluator {
	
	
	// The basic evaluation function consists of summing up 4 helper evaluation functions
	public static int evaluate(BoardHelper boardHelper, boolean showDetails)
	{
		int sum = 0;
		
		sum += transitionEvaluator (boardHelper);
		if (showDetails)
			System.out.print("Transition evaluator " + sum);
		
		sum += gapEvaluator(boardHelper);
		if (showDetails)
			System.out.print("\twith gap evaluator " + sum);
		
		sum += flatnessEvaluator(boardHelper);
		if (showDetails)
			System.out.println("\twith flatness evaluator " + sum);
		
		sum+= endsEvaluator(boardHelper);
		/*if (showDetails)
			System.out.println("\twith ends evaluator " + sum);
		sum+=extremeHeightsEvaluator(boardHelper);*/
		
		if (showDetails)
			System.out.println("\ttotal: " + sum);
		return sum;
	}
	
	// the default evaluation function does not print detailed evaluation results to sysout
	public static int evaluate(BoardHelper board)
	{
		return evaluate (board, false);
	}
	
	
	// the transition evaluation sums up the absolute values of the differences between two neighboring columns in the board.
	// It produces the best value for the board position with all column
	// heights being the same. It also gives extra penalty to the positions with height jumps
	// larger than 2 squares between neighboring columns
	public static int transitionEvaluator(BoardHelper board)
	{
		int sum = 0;
		int difference;
		
		for (int col = 1; col < board.getHeights().size(); col ++)
		{
			difference = Math.abs(board.getHeights().get(col) - board.getHeights().get(col-1));
			sum += difference;
			if (difference > 2)
				sum+=((difference-2) * (difference-2));
		}
			
		return sum;
	}
	
	// gap is defined as empty square in a column that has a nonempty square above it
	// gap Evaluator gives large penalties for the rows with gaps
	// The first gap in a given row adds 8 penalty points to a position and further gaps 4 penalty
	// points each
	public static int gapEvaluator (BoardHelper board)
	{
		int sum = 0;
		
		for (int row = board.getGaps().size()-1; row >=0; row--)
			if (board.getGaps().get(row).numEmpties.size() >0)
				sum+=(8 + (board.getGaps().get(row).numEmpties.size()-1)*4);
		return sum;
	}
	
	// this function is designed to work  together with
	// @transitionEvaluator()
	// while the optimal position from @transitionEvaluator() would be completely flat a simple
	// observation that s-shape and inverted s-shape would produce gaps when inserted into the
	// completely flat board results in following modifications
	// instead of favoring completely flat positions favor position that are flat for 2 squares
	// then have height change of 1 square then flat for 2 squares again and so on by giving
	// slight bonuses to positions with such a terrain
	// and a small penalty for completely flat positions
	public static int flatnessEvaluator(BoardHelper board)
	{
		int sum = 0;
		
		
		 int prevHeight = board.getHeights().get(0);
		 boolean heightChanged = false;
		 boolean isCurrentFlat = false;
		 boolean isPreviousFlat = false;
		 int height;
		 
		 for (int col = 1; col < board.getHeights().size(); col++ )
		 {
			 height = board.getHeights().get(col);
			 if (height == prevHeight)
			 {
				 isCurrentFlat = true;
				 if (isPreviousFlat)
				 {
					 sum --;
					 isPreviousFlat = false;
				 }
			 }
			 else 
			 {
				 if (Math.abs(height - prevHeight) <2)
					 heightChanged = true;
				 if (isCurrentFlat && Math.abs(height - prevHeight) <2 )
				 {
					 isPreviousFlat = true;
					 sum--;
				 }
				 else
					 isPreviousFlat = false;
				 
				 isCurrentFlat = false;
			 }
			 prevHeight = height;
			 
		 }
		 if (!heightChanged)
			 sum+=2;
		return sum;
	}
	
	// this function only works for even number of columns modify accordingly if numCol is odd
	@Deprecated
	// this function is not used here, rather similar function is used
	// as part of AiGameField.optimize() tie-breaker 
	public static int unevennessEvaluator (BoardHelper board)
	{
		int highest;
		int lowest;
		if (board.getHeights().get(0) > board.getHeights().get(1))
		{
			highest = board.getHeights().get(0);
			lowest = board.getHeights().get(1);
		}
		else
		{
			highest = board.getHeights().get(1);
			lowest = board.getHeights().get(0);
		}
		
		for (int col = 2; col <board.getHeights().size(); col+=2)
			if (board.getHeights().get(col) > board.getHeights().get(col+1))
			{
				if (highest < board.getHeights().get(col))
					highest = board.getHeights().get(col);
				if (lowest > board.getHeights().get(col+1))
					lowest = board.getHeights().get(col+1);
			}
			else
			{
				if (highest < board.getHeights().get(col+1))
					highest = board.getHeights().get(col+1);
				if (lowest > board.getHeights().get(col))
					lowest = board.getHeights().get(col);
			}
			
				
		
		return (highest-lowest);
	}
	
	
	// this evaluation function is designed to reduce penalties for positions with elevated 
	// 0 and board.size -1 columns
	// similar to the flatness evaluator that helps prepare the optimal position for 
	// s and inverted-s shapes, this allows dropping line-shapes against the walls without incurring large penalties
	public static int endsEvaluator (BoardHelper board)
	{
		int sum = 0;
		int difference1 = board.getHeights().get(0) - board.getHeights().get(1);
		int difference2 = board.getHeights().get(TetrisContract.BOARD_SIZE_X -1) - board.getHeights().get(TetrisContract.BOARD_SIZE_X-2);
		
		if (difference1 > 2)
			sum-=((difference1 - 2) * (difference1 -2));
		if (difference1 > 3)
			sum += ((difference1 - 4) * (difference1-3));
		
		if (difference2 > 2)
			sum-=((difference2 - 2) * (difference2 -2));
		if (difference2 > 3)
			sum += ((difference2 - 4) * (difference2-3));
		
		
		
		return sum;
	}
	
	
	// prevents the ai from creating positions that block further shape movement it is only evaluated
	// when one of the column heights exceeds shape insertion height
	public static int extremeHeightsEvaluator (BoardHelper helper)
	{
		int sum = 0;
		if (helper.getMaxHeight() <= 13)
			return sum;
		else
		{
			int middle = helper.getHeights().size()/2 -1;
			boolean encountered14left = helper.getHeights().get(middle)>=14;
			boolean encountered14right = helper.getHeights().get(middle +1)>=14;
			for (int i=middle-2; i >=0; i--)
			{
				int heightLeft = helper.getHeights().get(i);
				int heightRight = helper.getHeights().get(11-i);
				if (encountered14left && 
						(heightLeft <14 ||
								(helper.getHeights().get(i+1)>=14 && helper.getHeights().get(i+1)>heightLeft)))
					sum +=1000; // extremely high penalty
				if (encountered14right && 
						(heightRight <14) ||
								(helper.getHeights().get(11-i-1)>=14) && helper.getHeights().get(11-i-1) >heightRight)
					sum +=1000;
				if (heightLeft >=14)
					encountered14left = true;
				if (heightRight >=14)
					encountered14right = true;
			}
			
		}
		return sum;
		
	}

}
