package com.tishbi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.seaglasslookandfeel.ui.SeaGlassTableHeaderUI.DefaultTableCellHeaderRenderer;

public class SermonHeaderCellRenderer extends DefaultTableCellHeaderRenderer
{
	public SermonHeaderCellRenderer()
	{
		super();
		setHorizontalAlignment(SwingConstants.CENTER);
		Dimension d = getPreferredSize();
		d.height=25;
		setPreferredSize(d);
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) 
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		c.setBackground(new Color(186,191,88));
	    return c;
	}
	
	
}
