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
import javax.swing.JPanel;

public class TetrisGamePanel extends JPanel {

	List<List<Character>> mBoard;
	List<List<JLabel>> mBoardLabels;

	TetrisGamePanel() {
		super();
		setSize(new Dimension(240, 360));
		setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		initializeBoard(12,18);
		initializeLabels();
		
		//drawBoard();

		
		
	}

	public void initializeBoard(int sizeX, int sizeY) 
	{
		Random random = new Random();
		int randomChar;
		char ch;

		mBoard = new ArrayList<>();
		List<Character> xRow;

		for (int i = 0; i < sizeY; i++) {
			xRow = new ArrayList<>();
			for (int j = 0; j < sizeX; j++) {
				//randomChar = random.nextInt(7);
				//ch = getChar(randomChar);
				
				ch = 'e';
				//System.out.println("Do I get here?");
				
				if (ch == 'e')
					ch = (i + j)%2 == 0? 'e':'d'; 

				xRow.add(ch);

			}
			mBoard.add(xRow);
		}

	}

	public char getChar(int random) {
		switch (random) {
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
	
	public void initializeLabels()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		
		gc.fill = GridBagConstraints.NONE;
		
		
		mBoardLabels = new ArrayList<>();
		List<JLabel> labelsRow;
		JLabel tempLabel;
		

		for (int i = 0; i < mBoard.size(); i++)
		{
			labelsRow = new ArrayList<>();
			gc.gridy = mBoard.size()-i-1;
		
			for (int j = 0; j < mBoard.get(i).size(); j++)
			{
				tempLabel = ImageDefinitions.getPicture(mBoard.get(i).get(j));
				labelsRow.add(tempLabel);
				
				gc.gridx = j;
				
				add(tempLabel, gc);
				
			}
			mBoardLabels.add(labelsRow);
		}
		
	}
	
	public void drawBoard()
	{
		
		
		for (int i = 0; i < mBoard.size(); i++)
			for (int j = 0; j < mBoard.size(); j++)
			{
				
			}
		
	}
}
