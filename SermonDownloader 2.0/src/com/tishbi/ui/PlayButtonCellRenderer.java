package com.tishbi.ui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

public class PlayButtonCellRenderer extends DefaultTableCellRenderer
{
	public PlayButtonCellRenderer()
	{
		super();
		setOpaque(true);
		setHorizontalAlignment(CENTER);
	}

	@Override
	protected void setValue(Object obj) 
	{
		if (obj instanceof Image)
			setIcon(new ImageIcon((Image) obj));
		else
			setText("No Image");
	}
}
