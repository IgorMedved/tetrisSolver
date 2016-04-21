package ai;

public class Pair
{
	private int mIndex;
	private int mValue;
	public Pair(int index, int value)
	{
		setIndex(index);
		setValue(value);
	}
	
	public Pair ()
	{
		this(-1,-1);
		
	}

	public int getIndex()
	{
		return mIndex;
	}

	public int getValue()
	{
		return mValue;
	}

	public void setIndex(int index)
	{
		mIndex = index;
	}

	public void setValue(int value)
	{
		mValue = value;
	}
	
	
}
