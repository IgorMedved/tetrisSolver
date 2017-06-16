package tetris_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import tetris_model.contracts.TetrisContract;
import tetris_ui.events.UI_EventListener;

// A panel at the bottom of the screen containing four user setting buttons
public class SettingsPanel extends JPanel{
	private JToggleButton mShowAIHelpBtn;
	private JToggleButton mPlayInAiModeBtn;
	private JButton mHelpBtn;
	private JButton mRestartBtn;
	private UI_EventListener mListener;
	
	
	public SettingsPanel ()
	{
		FlowLayout mLayout = new FlowLayout();
		setLayout (mLayout);
		
		
		setBackground(TetrisContract.LIGHT_BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		setPreferredSize(new Dimension(606, 40));
		
		// initialize buttons
		mShowAIHelpBtn = new JToggleButton (TetrisContract.SHOW_HINTS_BUTTON_LABEL_DEPRESSED);
		mPlayInAiModeBtn = new JToggleButton (TetrisContract.AI_MODE_BUTTON_LABEL_DEPRESSED);
		mRestartBtn = new JButton(TetrisContract.RESTART_BUTTON_LABEL);
		mHelpBtn = new JButton(TetrisContract.HELP_BUTTON_LABEL);
		
		// set names for buttons
		mShowAIHelpBtn.setName(TetrisContract.SHOW_HINTS_BUTTON);
		mPlayInAiModeBtn.setName(TetrisContract.AI_MODE_BUTTON);
		mRestartBtn.setName(TetrisContract.RESTART_BUTTON);
		mHelpBtn.setName(TetrisContract.HELP_BUTTON);
		
		// set mnemonics for buttons
		mShowAIHelpBtn.setMnemonic(KeyEvent.VK_I);
		mPlayInAiModeBtn.setMnemonic(KeyEvent.VK_A);
		mRestartBtn.setMnemonic(KeyEvent.VK_S);
		mHelpBtn.setMnemonic(KeyEvent.VK_H);
		
		mShowAIHelpBtn.setForeground(Color.BLUE);
		mShowAIHelpBtn.setBackground(Color.WHITE);
		mPlayInAiModeBtn.setForeground(Color.BLUE);
		mPlayInAiModeBtn.setBackground(Color.WHITE);
		mRestartBtn.setForeground(Color.BLUE);
		mRestartBtn.setBackground(Color.MAGENTA);
		mHelpBtn.setForeground(Color.BLUE);
		mHelpBtn.setBackground(Color.WHITE);
		
		//mShowAIHelpBtn.addItemListener(mListener);
		
		
		add(mShowAIHelpBtn);
		add(mPlayInAiModeBtn);
		add(mRestartBtn);
		add(mHelpBtn);

		
	}
	
	public void setItemListener(ItemListener listener)
	{
		mShowAIHelpBtn.addItemListener(listener);
		mPlayInAiModeBtn.addItemListener(listener);
	}
	
	
	public void setActionListener (ActionListener listener)
	{
		mHelpBtn.addActionListener(listener);
		mRestartBtn.addActionListener(listener);
	}
	
	
	public void pressHelpButton()
	{
		mHelpBtn.doClick();
	}
	
	
	public void pressShowHintsButton()
	{
		mShowAIHelpBtn.doClick();
	}
	
	
	public void pressAIModeButton()
	{
		mPlayInAiModeBtn.doClick();
	}


	public void pressRestartButton() {
		mRestartBtn.doClick();
	}
}
