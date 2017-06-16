package tetris_model.contracts;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import tetris_model.Point;

public class TetrisContract
{
	// constants for different tetris board object types
	public static final int S_SHAPE = 0;
	public static final int INVERTED_S_SHAPE = 1;
	public static final int LINE_SHAPE = 2;
	public static final int T_SHAPE = 3;
	public static final int L_SHAPE = 4;
	public static final int INVERTED_L_SHAPE = 5;
	public static final int SQUARE_SHAPE = 6;
	public static final int EMPTY_SQUARE_LIGHT =7;
	public static final int EMPTY_SQUARE = 7; // same as empty square light
	public static final int EMPTY_SQUARE_DARK = 8;
	
	
	// constants for picture resource objects
	
	public static final int COVER = 10;
	public static final int HIRE = 11;
	public static final int PLAY_BTN = 12;
	public static final int PAUSE_BTN = 13;
	public static final int LEFT_ARROW = 14;
	public static final int RIGHT_ARROW = 15;
	public static final int TURN_ARROW = 16;
	public static final int DOWN_ARROW = 17;
	
	
	// color Definition
	public static final Color LIGHT_BLUE = new Color(144,208,255);
	
	
	// component name definitions for components triggering game or interface events
	
	public static final String PLAY_BUTTON = "playButton";
	public static final String HELP_BUTTON = "helpButton";
	public static final String SHOW_HINTS_BUTTON = "showHints";
	public static final String AI_MODE_BUTTON = "aiMode";
	public static final String RESTART_BUTTON = "restartButton";
	
	// Button labels
	public static final String SHOW_HINTS_BUTTON_LABEL_DEPRESSED = "Show Hints";
	public static final String SHOW_HINTS_BUTTON_LABEL_PRESSED = "Disable Hints";
	public static final String AI_MODE_BUTTON_LABEL_DEPRESSED = "Enter Auto Mode";
	public static final String AI_MODE_BUTTON_LABEL_PRESSED = "Exit Auto Mode";
	public static final String RESTART_BUTTON_LABEL = "Start New Game";
	public static final String HELP_BUTTON_LABEL = "Help";
			
	
	// Board size constants
	public static final int BOARD_SIZE_X = 12;
	public static final int BOARD_SIZE_Y = 18;
	
	public static final int NEXT_SHAPE_BOARD_X = 4;
	public static final int NEXT_SHAPE_BOARD_Y = 2;
	
	// Shape insertion location
	public static Point INITIAL_POINT = new Point (3, TetrisContract.BOARD_SIZE_Y-4);
	
	
	
	
	
		
}
