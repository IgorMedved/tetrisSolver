package model;


import java.util.ArrayList;
import java.util.List;

public class GameField
{
	public static final boolean FILLED = true;
	public static final boolean EMPTY = false;
	
	private static boolean isFilled;
	
	private List<List<Boolean>> mGameField;
	
	public GameField (int sizeX, int sizeY)
	{
		initializeGameField (sizeX, sizeY );
	
	}
	
	private void initializeGameField(int sizeX, int sizeY)
	{
		mGameField = new ArrayList<>();
		List<Boolean> xRow;
		
		for (int i = 0; i < sizeY ; i++)
		{
			xRow = new ArrayList<>();
			for (int j = 0; j < sizeX; j ++ )
			{
				xRow.add(EMPTY);
				
			}
			mGameField.add(xRow);
		}
		
	}
	
	public static GameField convertPointsToGameField(List<Point> points, int sizeX, int sizeY)
	{
		GameField field = new GameField(sizeX, sizeY);
		
		
		
		for(Point point: points)
		{
			field.fillPoint (point.getX(), point.getY());
		}
		
		
		
		return field;
	}
	
	public void fillPoint (int x, int y)
	{
		mGameField.get(y).set(x,FILLED);
	}
	
	public void emptyPoint (int x, int y)
	{
		mGameField.get(y).set(x, EMPTY);
	}
	
	public int getHeight()
	{
		return mGameField.size();
	}
	
	public int getWidth()
	{
		return mGameField.get(0).size();
	}
	
	public boolean getContentAtPosition (int x, int y)
	{
		return mGameField.get(y).get(x);
	}
	
	public static void printGameField (GameField field)
	{
		System.out.println();
		
		for (int i = field.getHeight() -1; i>=0; i --)
		{
			for (int j = 0; j < field.getWidth(); j ++)
			   if (field.getContentAtPosition(j,i)==FILLED)
			   	 System.out.print("x");
			   else
			     System.out.print("0");
			     
			System.out.println();
		}
		
		System.out.println();
		
	}
}