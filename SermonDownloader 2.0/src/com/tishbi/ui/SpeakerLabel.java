package com.tishbi.ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.tishbi.sitecontent.Speaker;

public class SpeakerLabel extends JLabel
{
	public Speaker speaker;
 
	public SpeakerLabel(Speaker _speaker)
	{
		this.speaker = _speaker;
		this.setText(_speaker.Name);
		if (_speaker.SpeakerImage != null)
		{
			this.setIcon(new ImageIcon(_speaker.SpeakerImage));
		}
	}
	
	public Speaker getSpeaker()
	{
		return this.speaker;
	}
}
