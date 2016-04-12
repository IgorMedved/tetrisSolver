package tetris_ui_test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class TetrisPanel extends JLayeredPane {
	protected List<List<JLabel>> mBackgroundLabels;
	protected List<List<JLabel>> mBoardLabels;
	
	private int mBoardSizeX;
	private int mBoardSizeY;
	
	public TetrisPanel(int boardSizeX, int boardSizeY)
	{
		super();
		mBoardSizeX = boardSizeX;
		mBoardSizeY = boardSizeY;
		
		setPreferredSize(new Dimension(mBoardSizeX*20+4, mBoardSizeY*20+4));
		setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
		
	}
	
	
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
							.getPicture((i + j) % 2 == 0 ? 'e' : 'd');
					labelsRow.add(tempLabel);

					gc.gridx = j;
					setLayer(tempLabel, 0);

					add(tempLabel, gc);

				}
				mBackgroundLabels.add(labelsRow);
			}
		}

	}
	
	public void initializeLabels(List<List<Character>> mBoard)
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
					if (mBoard == null || mBoard.get(i).get(j)=='e')
					{
						tempLabel = new JLabel ("e");
						setLayer(tempLabel, 0);
					}
					else
					{
						
						tempLabel = ImageDefinitions
							.getPicture(mBoard.get(i).get(j));
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
	
	public void showBoard(List<List<Character>> mBoard)
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
					if (mBoard == null || mBoard.get(i).get(j)== 'e')
						setLayer(mBoardLabels.get(i).get(j), 0);
					else
					{
						mBoardLabels.get(i).set(j, ImageDefinitions
								.getPicture(mBoard.get(i).get(j)));
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
		default:
			return 'e'; // empty square

		}
	}
}
