package tetris_ui;

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
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import tetris_model.Point;
import tetris_model.contracts.TetrisContract;


// a pane that is used to represent mainBoard and next shape boards on a screen
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
		showBoardBackground();
		
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
	public void initializeLabels(List<List<Integer>> board)
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
					if (board == null || board.get(i).get(j)==TetrisContract.EMPTY_SQUARE_LIGHT)
					{
						tempLabel = (i+j)%2 == 0? ImageDefinitions
								.getPictureAsLabel(TetrisContract.EMPTY_SQUARE_LIGHT):ImageDefinitions
								.getPictureAsLabel(TetrisContract.EMPTY_SQUARE_DARK);
						setLayer(tempLabel, 0);
					}
					else
					{
						
						tempLabel = ImageDefinitions
							.getPictureAsLabel(board.get(i).get(j));
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
	
	
	// shows the board passed to this method in UI. 
	//If mBoard == null it will show empty board
	public synchronized void showBoard(List<List<Integer>> mBoard, List<Point> shape, int shapeType, List<Point> hintsLocations)
	{
		if (mBoardLabels == null)
		{
			initializeLabels(mBoard);
				
		}
			for (int i = 0; i < mBoardSizeY; i++)
			{
				

				for (int j = 0; j < mBoardSizeX; j++)
				{
					if (mBoard == null || mBoard.get(i).get(j)== TetrisContract.EMPTY_SQUARE_LIGHT)
						setLayer(mBoardLabels.get(i).get(j), 0);
					else
					{
						
						mBoardLabels.get(i).get(j).setIcon(ImageDefinitions.getIcon(mBoard.get(i).get(j)));
						setLayer (mBoardLabels.get(i).get(j), 1);
					}
						
					

					

				}
				
			}
			if (shape!=null)
			{
				for (Point point:shape)
				{
					//System.out.println("Point: " + point.getX() +" " + point.getY());
					mBoardLabels.get(point.getY()).get(point.getX()).setIcon(ImageDefinitions.getIcon(shapeType));
					setLayer(mBoardLabels.get(point.getY()).get(point.getX()), 1);
					
				}
			}
			
			if (hintsLocations != null && hintsLocations.size()!=0)
			{
				Point point;
				Icon icon;
				for (int i = 0; i < hintsLocations.size(); i++)
				{
					point = hintsLocations.get(i);
					switch (i)
					{
					case 0:
						icon = ImageDefinitions.getIcon(TetrisContract.LEFT_ARROW);
						break;
					case 1:
						icon = ImageDefinitions.getIcon(TetrisContract.TURN_ARROW);
						break;
					case 2:
						icon = ImageDefinitions.getIcon(TetrisContract.RIGHT_ARROW);
						break;
						default:
							icon = ImageDefinitions.getIcon(TetrisContract.DOWN_ARROW);
					}
					if (point.getX()>=0 && point.getX()< TetrisContract.BOARD_SIZE_X){
						mBoardLabels.get(point.getY()).get(point.getX()).setIcon(icon);
						setLayer(mBoardLabels.get(point.getY()).get(point.getX()), 1);
					}
				}
			}
	}
}
