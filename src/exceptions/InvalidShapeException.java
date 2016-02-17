package exceptions;

import model.shapes.BlockShapeDefinitions;

public class InvalidShapeException extends Exception
{
	private final static String  DEFAULT_MESSAGE = "The shapeType for creating tetris figure is invalid. shape type should be between 0 and " + (BlockShapeDefinitions.getNumShapes())+
			"The shape type supplied was ";
	
	public InvalidShapeException (int shapeType)
	{
		super (DEFAULT_MESSAGE + shapeType);
	}
}
