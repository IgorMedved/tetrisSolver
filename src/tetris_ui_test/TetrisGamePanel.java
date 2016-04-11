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

public class TetrisGamePanel extends JLayeredPane
{

	List<List<Character>> mBoard;
	List<List<JLabel>> mBackgoundLabels;
	List<List<JLabel>> mBoardLabels;

	TetrisGamePanel()
	{
		super();
		setSize(new Dimension(240, 360));
		setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		initializeBoard(12, 18);
		showBoardBackground();
		showBoard();

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

	public void showBoardBackground()
	{
		if (mBackgoundLabels == null)
		{
			setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();

			gc.fill = GridBagConstraints.NONE;

			mBackgoundLabels = new ArrayList<>();
			List<JLabel> labelsRow;
			JLabel tempLabel;

			for (int i = 0; i < mBoard.size(); i++)
			{
				labelsRow = new ArrayList<>();
				gc.gridy = mBoard.size() - i - 1;

				for (int j = 0; j < mBoard.get(i).size(); j++)
				{
					tempLabel = ImageDefinitions
							.getPicture((i + j) % 2 == 0 ? 'e' : 'd');
					labelsRow.add(tempLabel);

					gc.gridx = j;
					setLayer(tempLabel, 0);

					add(tempLabel, gc);

				}
				mBackgoundLabels.add(labelsRow);
			}
		}

	}

	public void initializeLabels()
	{
		if (mBoardLabels == null)
		{
			mBoardLabels = new ArrayList<>();
			List<JLabel> labelsRow;
			JLabel tempLabel;
			GridBagConstraints gc = new GridBagConstraints();

			for (int i = 0; i < mBoard.size(); i++)
			{
				labelsRow = new ArrayList<>();
				gc.gridy = mBoard.size() - i - 1;

				for (int j = 0; j < mBoard.get(i).size(); j++)
				{
					
						tempLabel = ImageDefinitions
							.getPicture(mBoard.get(i).get(j));
					labelsRow.add(tempLabel);

					gc.gridx = j;
					if (mBoard.get(i).get(j)!='e')
						setLayer (tempLabel, 1);
					else
						setLayer(tempLabel, 0);

					add(tempLabel, gc);

				}
				mBoardLabels.add(labelsRow);
			}
		}
	}

	public void showBoard()
	{
		
		if (mBoardLabels == null)
		{
			initializeLabels();
				
		}
		else
		{
			for (int i = 0; i < mBoard.size(); i++)
			{
				

				for (int j = 0; j < mBoard.get(i).size(); j++)
				{
					
						
					if (mBoard.get(i).get(j)!='e')
					{
						mBoardLabels.get(i).set(j, ImageDefinitions
								.getPicture(mBoard.get(i).get(j)));
						setLayer (mBoardLabels.get(i).get(j), 1);
					}
						
					else
						setLayer(mBoardLabels.get(i).get(j), 0);

					

				}
				
			}
		}
		
	}
}
