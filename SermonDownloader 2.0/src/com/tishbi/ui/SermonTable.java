package com.tishbi.ui;

import java.awt.Cursor;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.tishbi.sitecontent.Sermon;
import com.tishbi.sitecontent.TableMappings;
import com.tishbi.util.Utilities;

public class SermonTable extends JTable  
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Sermon> sermons = null;
	private ArrayList<String> columnModel = null;
	
	public SermonTable(ArrayList<Sermon> _sermons)
	{
		setRowHeight(25);
		setAutoCreateRowSorter(true);
		setModel(new SermonTableModel(_sermons));
		removeColumn(getColumnModel().getColumn(4));
		removeColumn(getColumnModel().getColumn(4));
		removeColumn(getColumnModel().getColumn(4));
	}
	
	public void createColumns()
	{
		columnModel = new ArrayList<String>();
		Field[] fields = Sermon.class.getDeclaredFields();
		for (Field f : fields)
		{
			TableMappings fieldAttributes = Utilities.MetaData(f); 
			if (fieldAttributes.Visible())
			{
				TableColumn col = new TableColumn();
				col.setHeaderValue(fieldAttributes.Header());
				columnModel.add(fieldAttributes.Header());
			}
		}
	}

	public void loadData()
	{
		if (this.sermons == null) return;
	}
	
	public void loadData(ArrayList<Sermon> _sermons)
	{
		Field[] fields = Sermon.class.getDeclaredFields();
		for (Field f : fields)
		{
			TableMappings fieldAttributes = Utilities.MetaData(f); 
			if (fieldAttributes.Visible())
			{
				TableColumn col = new TableColumn();
				col.setHeaderValue(fieldAttributes.Header());
				columnModel.add(fieldAttributes.Header());
			}
		}
	}
}
