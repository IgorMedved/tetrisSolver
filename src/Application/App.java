package Application;



import model.shapes.Shape;
import model.shapes.Shape_S;

public class App
{
	public static void main (String[] args) throws java.lang.Exception
	{
		//Shape_S.printAllOrientationsForShape();
		
		Shape shapeS = new Shape_S();
		shapeS.printInitialShape();
		shapeS.printCurrentOrientation();
		shapeS.printNextOrientation();
		
		shapeS.rotateShape();
		
		shapeS.printCurrentOrientation();
		shapeS.printNextOrientation();
		
		Shape shapeNext = shapeS.getNextOrientation();
		shapeNext.printCurrentOrientation();
		shapeNext.getNextOrientation().printCurrentOrientation();
		
	}
}
