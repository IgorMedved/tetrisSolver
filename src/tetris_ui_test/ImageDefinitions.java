package tetris_ui_test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageDefinitions  extends JPanel  {
	
	
	
	private static BufferedImage EMPTYLIGHT; 
	private static BufferedImage EMPTYDARK;
	private static BufferedImage LINE; 
	private static BufferedImage SQUARE; 
	private static BufferedImage L_SHAPE; 
	private static BufferedImage INVERSE_L; 
	private static BufferedImage S_SHAPE; 
	private static BufferedImage INVERSE_S;
	private static BufferedImage COVER;
	private static BufferedImage PLAY;
	private static BufferedImage PAUSE;
	private static BufferedImage HIRE;
	
	
	
	
	
	public static void initializePictures()
	{
		String workingDir = System.getProperty("user.dir");
		String resources = workingDir + File.separatorChar+ "resources";
		
		try
		{
		EMPTYLIGHT = ImageIO.read(new File(resources + File.separatorChar + "tetrisLight1.png"));
		EMPTYDARK = ImageIO.read(new File(resources + File.separatorChar + "tetrisDark.png"));
		LINE = ImageIO.read(new File(resources + File.separatorChar + "lineShape.png"));
		SQUARE = ImageIO.read(new File(resources + File.separatorChar + "square.png"));
		L_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "lShape.png"));
		INVERSE_L = ImageIO.read(new File(resources + File.separatorChar + "inverseL.png"));
		S_SHAPE = ImageIO.read(new File(resources + File.separatorChar + "sShape.png"));
		INVERSE_S = ImageIO.read(new File(resources + File.separatorChar + "inverseS.png"));
		COVER = ImageIO.read(new File(resources + File.separatorChar + "palm.png"));
		HIRE = ImageIO.read(new File(resources + File.separatorChar + "programming.png"));
		PLAY = ImageIO.read(new File(resources + File.separatorChar + "play1.png"));
		PAUSE =ImageIO.read(new File(resources + File.separatorChar + "pause.png"));
		
		}
		catch (IOException e)
		{
			System.out.println("wrong file path" + e.getMessage());
		}
		
	}
	
	public static JLabel getPicture(char type)
	{
		switch (type)
		{
		case 'i':
			return new JLabel (new ImageIcon(LINE));
		case 's':
			return new JLabel (new ImageIcon(S_SHAPE));
		case '2':
			return new JLabel (new ImageIcon(INVERSE_S));
		case 'q':
			return new JLabel (new ImageIcon(SQUARE));
		case 'l':
			return new JLabel (new ImageIcon(L_SHAPE));
		case 'h':
			return new JLabel (new ImageIcon(INVERSE_L));
		case 'e':
			return new JLabel (new ImageIcon(EMPTYLIGHT));
		case 'd':
			return new JLabel (new ImageIcon(EMPTYDARK));
		case 'c':
			return new JLabel (new ImageIcon(COVER));
		case 'm':
			return new JLabel (new ImageIcon(HIRE));
		case 'p':
			return new JLabel (new ImageIcon(PLAY));
		case 'u':
			return new JLabel (new ImageIcon(PAUSE));
		default:
				return null;
			
		}
	}
	
	

	

}
