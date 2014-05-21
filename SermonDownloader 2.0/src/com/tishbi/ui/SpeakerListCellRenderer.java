package com.tishbi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.tishbi.sitecontent.SermonIndexCache;
import com.tishbi.sitecontent.Speaker;
import com.tishbi.util.Globals;

public class SpeakerListCellRenderer extends JLabel implements ListCellRenderer
{
	public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) 
	{
		if (!(obj instanceof Speaker))
			return null;
		
		Speaker _speaker = (Speaker) obj;
		Image newimg;

		setOpaque(true);
		setText(_speaker.Name.trim().length() > 20 ? _speaker.Name.substring(0, 19) : _speaker.Name );
		setToolTipText("<html><b>" + _speaker.Name + "</b></html>");
		setBorder(new EmptyBorder(1,1,1,1));
		
		if (_speaker.SpeakerImage != null)
			newimg = _speaker.SpeakerImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
		else
			newimg = SermonIndexCache.speakerDefaultImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
		
		setIcon(new ImageIcon(newimg));
		setBorder(isSelected ? Globals.selectedBorder : Globals.unselectedBorder);
		setBackground(isSelected ? new Color(217,217,150) : new Color(140, 140, 90));
		setForeground(isSelected ? list.getBackground() : Color.BLACK);
		
		return this;
	}
}
