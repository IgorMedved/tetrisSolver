package tetris_ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.contracts.TetrisContract;

public class ImageDefinitions  extends JPanel  {
	
	
	
	private static BufferedImage EMPTYLIGHT; 
	private static BufferedImage EMPTYDARK;
	private static BufferedImage LINE_SHAPE; 
	private static BufferedImage SQUARE_SHAPE; 
	private static BufferedImage L_SHAPE; 
	private static BufferedImage INVERTED_L_SHAPE; 
	private static BufferedImage S_SHAPE; 
	private static BufferedImage INVERSE_S;
	private static BufferedImage T_SHAPE;
	private static BufferedImage COVER;
	private static BufferedImage PLAY_BTN;
	private static BufferedImage PAUSE_BTN;
	private static BufferedImage HIRE;
	
	
	
	
	
	public static void initializePictures()
	{
		String workingDir = System.getProperty("user.dir");
		String resources = workingDir + File.separatorChar+ "resources";
		
		try
		{
		EMPTYLIGHT = ImageIO.read(new File(resources + File.separatorChar + "tetrisLight1.png"));
		EMPTYDARK = ImageIO.read(new File(resources + File.separatorChar + "tetrisDark.png"));
		LINE_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "lineShape.png"));
		SQUARE_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "square.png"));
		L_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "lShape.png"));
		INVERTED_L_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "inverseL.png"));
		S_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "sShape.png"));
		INVERSE_S = ImageIO.read(new File(resources + File.separatorChar + "inverseS.png"));
		T_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "tShape.png"));
		COVER = ImageIO.read(new File(resources + File.separatorChar + "palm.png"));
		HIRE = ImageIO.read(new File(resources + File.separatorChar + "programming.png"));
		PLAY_BTN = ImageIO.read(new File(resources + File.separatorChar + "playblue.png"));
		PAUSE_BTN =ImageIO.read(new File(resources + File.separatorChar + "pauseblue.png"));
		
		}
		catch (IOException e)
		{
			System.out.println("wrong file path" + e.getMessage());
		}
		
	}
	
	public static JLabel getPictureAsLabel(int resourceID)
	{
		
		return new JLabel(getIcon(resourceID));	
		
	}
	
	public static ImageIcon getIcon(int resourceID)
	{
		return new ImageIcon (getImage(resourceID));
	}
	
	public static BufferedImage getImage(int resourceID)
	{
		switch(resourceID)
		{
		case TetrisContract.S_SHAPE:
			return S_SHAPE;
		case TetrisContract.INVERTED_S_SHAPE:
			return INVERSE_S;
		case TetrisContract.LINE_SHAPE:
			return LINE_SHAPE;
		case TetrisContract.T_SHAPE:
			return T_SHAPE;
			
		case TetrisContract.L_SHAPE:
			return L_SHAPE;
		case TetrisContract.INVERTED_L_SHAPE:
			return INVERTED_L_SHAPE ;
		case TetrisContract.SQUARE_SHAPE:
			return SQUARE_SHAPE;
		case TetrisContract.EMPTY_SQUARE_LIGHT:
			return EMPTYLIGHT;
		case TetrisContract.EMPTY_SQUARE_DARK:
			return EMPTYDARK;
		case TetrisContract.COVER:
			return COVER;
		case TetrisContract.HIRE:
			return HIRE;
		case TetrisContract.PLAY_BTN:
			return PLAY_BTN;
		case TetrisContract.PAUSE_BTN:
			return PAUSE_BTN;
		
		default:
			return null;
			
			
		}
			
		
		
			
			
		
	}
	
	

	

}
