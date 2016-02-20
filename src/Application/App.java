package Application;





import controller.Controller;


public class App
{
	public static void main (String[] args) throws java.lang.Exception
	{
		//Shape_S.printAllOrientationsForShape();
		
		/*Shape shape;
		
		for (int i = 0; i < BlockShapeDefinitions.getNumShapes(); i++)
		{
			shape = new Shape(i);
			shape.printAllOrientationsForShape();
			shape.printCurrentOrientation();
			shape.printNextOrientation();
			
			
		}*/
		
		/*GameField myField = new GameField(GameField.BOARD_SIZE_X, GameField.BOARD_SIZE_Y);
		myField.startMove();
		myField.drop();
		myField.drop();
		for (int i = 0; i <20; i++)
		{
			myField.drop();
			
		}*/
		
		controller.Controller mController = new Controller ();
		mController.run();
		
		
		
	}
}
