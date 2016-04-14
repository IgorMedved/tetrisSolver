package tetris_ui;

/*
 * This class is for a small panel showing next shape on the screen
 * Same as TetrisGamePanel it is based on TetrisPanel
 */

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

public class NextFigurePanel extends TetrisPanel{
	
	//private List<List<Integer>> mBoard;
	
	NextFigurePanel()
	{
		super(4,2);
		
		//initializeBoard(4,2);
		showBoardBackground();
		showBoard(null);
		
	}
	
	
	/*public void initializeBoard(int sizeX, int sizeY)
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

	}*/
}
	
	