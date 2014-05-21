package com.tishbi.ui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.tishbi.sitecontent.Speaker;
import com.tishbi.util.Globals;

public class SpeakerListItem extends JLabel 
{
	private static final long serialVersionUID = 1L;
	private Speaker speaker;
	
	public SpeakerListItem(Speaker speaker) 
	{
		setOpaque(true);
		this.speaker = speaker;
		Image newimg;
//		if (speaker.SpeakerImage != null)
//			newimg = speaker.SpeakerImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
//		else
//			newimg = new ImageIcon(Globals.NoImage()).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);;
		setText(speaker.Name);
		setIcon(new ImageIcon(speaker.SpeakerImage));
		setToolTipText("<html><h2>" + speaker.Name + "</h2></html>");		
		setBorder(new EmptyBorder(1,1,1,1));
	}
	
	public Speaker getSpeaker()
	{
		return this.speaker;
	}

}
