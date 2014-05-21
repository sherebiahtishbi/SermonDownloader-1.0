package com.tishbi.ui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.border.BevelBorder;

import com.tishbi.sitecontent.Speaker;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.MatteBorder;

public class SpeakerPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	public Speaker speaker;
	public JLabel SpeakerName;
	public JLabel SpeakerImage;

	public SpeakerPanel(Speaker _speaker) 
	{
		this.speaker = _speaker;
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setOpaque(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{193, 78, 0};
		gridBagLayout.rowHeights = new int[]{60, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		SpeakerName = new JLabel("Speaker Name");
		SpeakerName.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		SpeakerName.setHorizontalAlignment(SwingConstants.LEFT);
		SpeakerName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_SpeakerName = new GridBagConstraints();
		gbc_SpeakerName.fill = GridBagConstraints.BOTH;
		gbc_SpeakerName.insets = new Insets(5, 5, 5, 5);
		gbc_SpeakerName.gridx = 0;
		gbc_SpeakerName.gridy = 0;
		SpeakerName.setOpaque(true);
		add(SpeakerName, gbc_SpeakerName);
		if (_speaker.Name != null)
		{
			if (_speaker.Name.length()>20)
				SpeakerName.setText(_speaker.Name.substring(0, 20)+"..");
			else
				SpeakerName.setText(_speaker.Name);
		}
		SpeakerImage = new JLabel();
		SpeakerImage.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_SpeakerImage = new GridBagConstraints();
		gbc_SpeakerImage.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc_SpeakerImage.insets = new Insets(2, 2, 2, 0);
		gbc_SpeakerImage.gridx = 1;
		gbc_SpeakerImage.gridy = 0;
		SpeakerImage.setOpaque(true);
		add(SpeakerImage, gbc_SpeakerImage);
		if (_speaker.SpeakerImage != null)
		{
			Image newimg = _speaker.SpeakerImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
			SpeakerImage.setIcon(new ImageIcon(newimg));
			SpeakerImage.setText("");
		}
		
		String toolTipText = "<html><h2>" +_speaker.Name + "</h2></html>";
		setToolTipText(toolTipText);

	}
	
	public Speaker getSpeaker()
	{
		return this.speaker;
	}

}
