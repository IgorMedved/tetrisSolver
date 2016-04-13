package tetris_ui_test;

/*
 * Base class for showing tetris board and various shapes on it
 * It is used as basis for TetrisGamePanel which shows the main game panel 
 * as well as NextFigurePanel which shows the next shape to be played after current turn is complete
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import model.contracts.TetrisContract;



public class TetrisPanel extends JLayeredPane {
	protected List<List<JLabel>> mBackgroundLabels; // Labels showing the board itself
	protected List<List<JLabel>> mBoardLabels; // labels showing shapes on the board
	
	private int mBoardSizeX; // the width of the board
	private int mBoardSizeY; // the height of the board
	
	public TetrisPanel(int boardSizeX, int boardSizeY)
	{
		super();
		mBoardSizeX = boardSizeX;
		mBoardSizeY = boardSizeY;
		
		setPreferredSize(new Dimension(mBoardSizeX*20+4, mBoardSizeY*20+4));
		setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
		
	}
	
	
	// initializes labels responsible for showing the the board itself and lays them accordingly
	// this method only needs to be run once
	public void showBoardBackground()
	{
		if (mBackgroundLabels == null)
		{
			setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();

			gc.fill = GridBagConstraints.NONE;

			mBackgroundLabels = new ArrayList<>();
			List<JLabel> labelsRow;
			JLabel tempLabel;

			for (int i = 0; i < mBoardSizeY; i++)
			{
				labelsRow = new ArrayList<>();
				gc.gridy = mBoardSizeY - i - 1;

				for (int j = 0; j < mBoardSizeX; j++)
				{
					tempLabel = ImageDefinitions
							.getPictureAsLabel((i + j) % 2 == 0 ? TetrisContract.EMPTY_SQUARE_LIGHT : TetrisContract.EMPTY_SQUARE_DARK);
					labelsRow.add(tempLabel);

					gc.gridx = j;
					setLayer(tempLabel, 0);

					add(tempLabel, gc);

				}
				mBackgroundLabels.add(labelsRow);
			}
		}

	}
	
	
	// initializes labels responsible for showing tetris shapes on the board
	public void initializeLabels(List<List<Integer>> mBoard)
	{
		if (mBoardLabels == null)
		{
			mBoardLabels = new ArrayList<>();
			List<JLabel> labelsRow;
			JLabel tempLabel;
			GridBagConstraints gc = new GridBagConstraints();

			for (int i = 0; i < mBoardSizeY; i++)
			{
				labelsRow = new ArrayList<>();
				gc.gridy = mBoardSizeY - i - 1;

				for (int j = 0; j < mBoardSizeX; j++)
				{
					if (mBoard == null || mBoard.get(i).get(j)==TetrisContract.EMPTY_SQUARE_LIGHT)
					{
						tempLabel = new JLabel ("e");
						setLayer(tempLabel, 0);
					}
					else
					{
						
						tempLabel = ImageDefinitions
							.getPictureAsLabel(mBoard.get(i).get(j));
						setLayer (tempLabel, 1);
					}
					labelsRow.add(tempLabel);

					gc.gridx = j;
					

					add(tempLabel, gc);

				}
				mBoardLabels.add(labelsRow);
			}
		}
	}
	
	public synchronized void showBoard(List<List<Integer>> mBoard)
	{
		
		if (mBoardLabels == null)
		{
			initializeLabels(mBoard);
				
		}
		else
		{
			for (int i = 0; i < mBoardSizeY; i++)
			{
				

				for (int j = 0; j < mBoardSizeX; j++)
				{
					if (mBoard == null || mBoard.get(i).get(j)== TetrisContract.EMPTY_SQUARE_LIGHT)
						setLayer(mBoardLabels.get(i).get(j), 0);
					else
					{
						mBoardLabels.get(i).set(j, ImageDefinitions
								.getPictureAsLabel(mBoard.get(i).get(j)));
						setLayer (mBoardLabels.get(i).get(j), 1);
					}
						
					

					

				}
				
			}
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
		case 7:
			return 't';
		default:
			return 'e'; // empty square

		}
	}
}
