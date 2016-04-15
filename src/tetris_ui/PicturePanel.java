package tetris_ui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.contracts.TetrisContract;

public class PicturePanel extends JPanel{
	private JLabel mCoverLabel;
	private BufferedImage mCoverPicture;
	private BufferedImage mHiddenPicture;
	
	public PicturePanel()
	{
		setPreferredSize(new Dimension(360,364));
		setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		setLayout(new BorderLayout());
		
		mCoverPicture = ImageDefinitions.getImage(TetrisContract.COVER);
		mHiddenPicture = ImageDefinitions.getImage(TetrisContract.HIRE);
		
		mCoverLabel = new JLabel(new ImageIcon(mHiddenPicture));
		
		
		
		
		add (mCoverLabel, BorderLayout.CENTER);
		
		updateCoverTransparency(255);
		
		
		
	}
	
	public void updateCoverTransparency (int opacity)
	{
		
		
		ImageDefinitions.reinitializeHidden();
		mHiddenPicture = ImageDefinitions.getImage(TetrisContract.HIRE);
		
		if (opacity >0)
		{
			Graphics2D g2d = (Graphics2D)mHiddenPicture.getGraphics();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacity/255));
			g2d.drawImage(mCoverPicture,0,0,null);
		
			mCoverLabel.setIcon(new ImageIcon(mHiddenPicture));
			
			//System.out.println("Drew picture with opacity " + opacity);
		}
		

	}

}
