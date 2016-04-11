package tetris_ui_test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NextFigurePanel extends JPanel{
	
	JLabel mTempLabel;
	List<List<Character>> mBoard;
	List<List<JLabel>> mLabels;
	
	NextFigurePanel()
	{
		//setLayout(new BorderLayout());
		setPreferredSize(new Dimension (84,44));
		//mTempLabel = new JLabel("nextFigure");
		setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
		
		inititializeBoard();
		initializeLabels();
		
		//add (mTempLabel, BorderLayout.CENTER);
	}
	
	
	public void inititializeBoard()
	{
		mBoard = new ArrayList<>();
		List<Character> xRow;
		char ch;
		for (int i = 0; i < 2; i ++)
		{
			xRow = new ArrayList<>();
			for (int j = 0; j < 4; j ++)
			{
				ch = (i+j)%2 == 0? 'e': 'd';
				xRow.add(ch);
			}
			mBoard.add(xRow);
		}
			
				
	}
	
	public void initializeLabels()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		
		gc.fill = GridBagConstraints.NONE;
		
		
		mLabels = new ArrayList<>();
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
			mLabels.add(labelsRow);
		}
		
	}
	
	
	

}
