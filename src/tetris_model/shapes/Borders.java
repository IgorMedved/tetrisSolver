package tetris_model.shapes;

// class that stores left, right, lower and upper border of each shape
public class Borders
{
	public int yDown;
	public int yUp;
	public int xLeft;
	public int xRight;
	public Borders(int yDown, int yUp, int xLeft, int xRight) {
		super();
		this.yDown = yDown;
		this.xLeft = xLeft;
		this.xRight = xRight;
		this.yUp=yUp;
	}
	
	
	public String toString()
	{
		return "Left border: " + xLeft + " right border: " + xRight + " lowerBorder " + yDown + " upper Border " + yUp;
	}
	
}



