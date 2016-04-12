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

public class TetrisGamePanel extends TetrisPanel
{

	List<List<Character>> mBoard;
	List<List<JLabel>> mBackGroundLabels;
	List<List<JLabel>> mBoardLabels;

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
		int randomChar;
		char ch;

		mBoard = new ArrayList<>();
		List<Character> xRow;

		for (int i = 0; i < sizeY; i++)
		{
			xRow = new ArrayList<>();
			for (int j = 0; j < sizeX; j++)
			{
				randomChar = random.nextInt(7);
				ch = getChar(randomChar);

				xRow.add(ch);

			}
			mBoard.add(xRow);
		}

	}

	public static char getChar(int random)
	{
		switch (random)
		{
		case 1:
			return 'i'; // line
		case 2:
			return 's'; // s-shape
		case 3:
			return '2'; // inverse s-shape (or 2)
		case 4:
			return 'q'; // square
		case 5:
			return 'l'; // l-shape
		case 6:
			return 'h'; // inverse l-sHape
		default:
			return 'e'; // empty square

		}
	}

}
