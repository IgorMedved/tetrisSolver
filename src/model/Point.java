package model;

public class Point
{
	public static final int INVALID_COORDINATE = -1;
	public static final int X_LOWER = 0;
	public static final int X_UPPER = 11;
	public static final int Y_LOWER = 0;
	public static final int Y_UPPER = 17;
	
	private int mX;
	private int mY;
	
	public Point (int x, int y)
	{
		setPoint (x,y);
	}
	
	public void setPoint (int x, int y)
	{
		/*if (Point.outOfBound(x,y))
		{
			mX = INVALID_COORDINATE;
			mY = INVALID_COORDINATE;
		}
		else*/
		{
			mX = x;
			mY = y;
		}
	}
	
	public int getX()
	{
		return mX;
	}
	
	public int getY()
	{
		return mY;
	}
	
	public boolean isValidPoint ()
	{
		return !outOfBound(mX, mY);
	}
	
	
	public static boolean outOfBoundX (int x)
	{
		return x < X_LOWER || x > X_UPPER; 
	}
	
	public static boolean outOfBoundY (int y)
	{
		return y < Y_LOWER || y > Y_UPPER;
	}
	
	public static boolean outOfBound (int x, int y)
	{
		return outOfBoundX(x) || outOfBoundY(y);
	}
	
	public boolean outOfBound()
	{
		return outOfBound(mX,mY);
	}
	
	public Point left()
	{
		return new Point(mX-1, mY);
	}
	
	public Point right()
	{
		return new Point (mX+1, mY);
	}
	
	public Point down()
	{
		return new Point (mX, mY-1);
	}
	
}
