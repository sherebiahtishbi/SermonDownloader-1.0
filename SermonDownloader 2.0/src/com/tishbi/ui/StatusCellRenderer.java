package com.tishbi.ui;

import java.awt.Color;
import java.awt.event.MouseMotionListener;

import javax.swing.table.DefaultTableCellRenderer;

public class StatusCellRenderer extends DefaultTableCellRenderer 
{
	public StatusCellRenderer()
	{
		super();
	}

	@Override
	protected void setValue(Object obj) 
	{
		String text = (String) obj;
		if (text.toUpperCase().contains("ALREADY"))
			setBackground(new Color(186,191,88));
		else if ((text.toUpperCase().contains("DOWNLOADING")))
			setBackground(new Color(221,224,119));
		else if ((text.toUpperCase().contains("COMPLETE")))
			setBackground(new Color(182,219,149));
		else if ((text.toUpperCase().contains("PLAYING")))
			setBackground(new Color(166,221,245));
		else
			setBackground(new Color(240,176,163));
		setText((obj == null) ? "" : text);
	}

}
