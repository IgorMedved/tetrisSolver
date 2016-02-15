package model.shapes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.GameField;
import model.Point;

public abstract class Shape
{
	protected List<Point> mShape = new ArrayList<>(4);
	protected int mOrientation;
	
	abstract public boolean rotateShape ();
	abstract protected void putShapeInOrientation(int orientation);
	
	abstract protected void empty();
	
	abstract public void printInitialShape ();
	abstract public void printCurrentOrientation ();
	abstract public void printNextOrientation();
	
	abstract public Shape getNextOrientation();
}

