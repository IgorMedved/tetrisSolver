package tetriswindow;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class UserInterface extends JPanel implements ActionListener
{
	private static final String PLAY = "|> Play";
	private static final String PAUSE  = "|| Pause";
	
	private static final String PAUSE_MESSAGE = "Game Paused";
	private static final String GAME_OVER = "GAME OVER!";
	private static final String START_GAME = "Press play button to start";
	
	private static final String AI = "AI Play";
	private static final String HUMAN_PLAY = "Human play";
	
	JButton mBtnPlayPause;
	JButton mBtnAIMode;
	

	JLabel mLblGameField;
	JLabel mLblgameOverPause;
	JLabel mLblNextFigure;
	JLabel mLblScore;
	

	TetrisMainScreenButtonPressedListener mListener;

	public UserInterface()
	{
		mBtnPlayPause = new JButton(PLAY);
		mBtnAIMode = new JButton(AI);
		
		mLblGameField = new JLabel();
		mLblgameOverPause = new JLabel(START_GAME);
		mLblNextFigure = new JLabel();

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.LAST_LINE_END;
		gc.gridx = 1;
		gc.gridy = 10;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(50, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;

		add(mBtnPlayPause, gc);
		
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		gc.gridx = 2;
		gc.gridy = 10;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(50, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;

		add(mBtnAIMode, gc);

		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.weightx = 2;
		gc.weighty = 10;
		gc.insets = new Insets(50, 10, 0, 0);

		gc.fill = GridBagConstraints.NONE;
		add(mLblGameField, gc);

		
/*
		btnGenerateReportFiles.addActionListener(this);
		btnSelectDir.addActionListener(this);
		btnValidateFiles.addActionListener(this);
		btnOpenLogFiles.addActionListener(this);

		btnGenerateReportFiles.setEnabled(false);
		btnValidateFiles.setEnabled(false);
		btnOpenLogFiles.setEnabled(false);*/

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == mBtnPlayPause)
			firePlayPauseEvent();
		else if (e.getSource() == mBtnAIMode)
			fireAIOnOffEvent();
		

	}
	
	private void firePlayPauseEvent()
	{
		
	}
	
	private void fireAIOnOffEvent()
	{
		
	}
	
	public void setListener(TetrisMainScreenButtonPressedListener listener)
	{
		mListener = listener;
	}
/*
	public void activateValidateButton()
	{
		btnGenerateReportFiles.setEnabled(false);
		btnValidateFiles.setEnabled(true);
		btnOpenLogFiles.setEnabled(false);
	}

	public void activateReportButton(boolean validationSuccessful)
	{
		if (validationSuccessful)
		{
			btnGenerateReportFiles.setEnabled(true);
			btnValidateFiles.setEnabled(true);
			btnOpenLogFiles.setEnabled(false);
		}

		else
		{
			btnGenerateReportFiles.setEnabled(false);
			btnValidateFiles.setEnabled(true);
			btnOpenLogFiles.setEnabled(false);
		}
	}

	private void fireDirectorySelectButtonPressedEvent()
	{
		if (mListener != null)
			mListener.onSelectButtonPressed();

	}

	private void fireValidateButtonPressedEvent()
	{
		if (mListener != null)
			mListener.onValidateButtonPressed();
	}

	private void fireLogButtonPressedEvent()
	{
		if (mListener != null)
			mListener.onLogButtonPressed();
	}

	private void fireReportButtonPressedEvent()
	{
		if (mListener != null)
			mListener.onReportButtonPressed();
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(600, 300);
	}

	

	public void setLblPath(String label)
	{
		lblPath.setText("Selected path: " + label);
	}

	public void setLblGenerate(String message)
	{
		lblGenerate.setText(message);
	}

	public void SetLblValidate(String message)
	{
		lblValidate.setText(message);
	}*/
}

