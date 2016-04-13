package tetris_ui_test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/* 
 * Class responsible for showing a game board and current state of the game
 * 
 * 
 */

public class TetrisGamePanel extends TetrisPanel
{

	private List<List<Integer>> mBoard;

	TetrisGamePanel()
	{
		super(12,18);
		
		initializeBoard(12, 18);
		showBoardBackground();
		showBoard(mBoard);

		// drawBoard();

	}

	public void initializeBoard(int sizeX, int sizeY)
	{
		Random random = new Random();
		int randomShape;

		mBoard = new ArrayList<>();
		List<Integer> xRow;

		for (int i = 0; i < sizeY; i++)
		{
			xRow = new ArrayList<>();
			for (int j = 0; j < sizeX; j++)
			{
				randomShape = random.nextInt(8);

				xRow.add(randomShape);

			}
			mBoard.add(xRow);
		}

	}

	

}
