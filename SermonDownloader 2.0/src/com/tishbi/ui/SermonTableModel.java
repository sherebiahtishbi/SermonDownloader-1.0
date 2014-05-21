package com.tishbi.ui;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import com.tishbi.sitecontent.Sermon;
import com.tishbi.sitecontent.SermonIndexCache;
import com.tishbi.sitecontent.TableMappings;
import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.util.Utilities;

public class SermonTableModel extends AbstractTableModel 
{
	private static final long serialVersionUID = 1L;

	private ArrayList<Sermon> sermons;
	private ArrayList<String> columns;
	private Map<String,Sermon> sermonLookup;
	Field[] properties = Sermon.class.getFields();
	
	public SermonTableModel(ArrayList<Sermon> _sermons)
	{
		CollectColumns();
		this.sermons = _sermons;
		CreateMap();
	}
	

	/*
	 * Implementation of the Abstract Model(non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() 
	{
		return sermons.size();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int index) 
	{
		String colname = null;
		if (index<columns.size())
			colname = columns.get(index);
		if (colname==null)
			colname = super.getColumnName(index);
		return colname;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) 
	{
	    Sermon _sermon = sermons.get(row);
	    switch(col)
	    {
	      case 0: 
	    	  return _sermon.Title;
	      case 1: 
	    	  return _sermon.Length;
	      case 2:
	      {
	    	  if (_sermon.Title.contains("Searching sermons...")) return "";
	    	  switch(_sermon.Status)
	    	  {
	    	  	case NOT_STARTED:
	    	  		return "Download";
				case DOWNLOADING:
					return "Downloading..";
				case ALREADY_DONE:
					return "Already Exists";
				case COMPLETED:
					return "Complete";
				case FAILED:
					return "Failed";
				case PLAYING:
					return "Playing";
	    	  }
	      }
	      case 3: 
	    	  return _sermon.isSermonSelected;
	      case 4:
		      {
		    	  switch (_sermon.Status)
		    	  {
		    	  case ALREADY_DONE:
		    		  return SermonIndexCache.getPlayButton(true); 
		    	  case COMPLETED:
		    		  return SermonIndexCache.getPlayButton(true); 
		    	  case PLAYING:
		    		  return SermonIndexCache.getPauseButton();
		    	  default:
		    		  return SermonIndexCache.getPlayButton(false);
		    	  }
		      }
		  default: 
		    	  return null;
	    }
	}
	
	/*
	 * Over load for getValueAt where whole Sermon object 
	 */
	public Sermon getValueAt(int row)
	{
		return sermons.get(row);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object obj, int row, int col)
	{
		if (col != 3) return;
	    Sermon _sermon = sermons.get(row);
	    _sermon.isSermonSelected =  (Boolean) obj;
	    fireTableCellUpdated(row, col); 
	}
	
	/*
	 * Over load for setValueAt where whole sermon object can be added to model
	 */
	public void setValueAt(Sermon sermon, int row)
	{
		sermons.remove(row);
		sermons.add(row, sermon);
		fireTableDataChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class getColumnClass(int index) 
	{
		Class columnclass = null;
		if (index<columns.size())
			columnclass= getValueAt(0,index).getClass();
		
		if (columnclass == null)
			columnclass= super.getColumnClass(index);
		return columnclass;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int col) 
	{
		if (col == 3 || col == 4)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() 
	{
		Field[] cols = Sermon.class.getFields();
		return cols.length;
	}
	
	/*
	 * All methods below are the custom implementations
	 */

	
	/*
	 * Creates a HashMap for random access of any object in the Sermon array list 
	 */
	private void CreateMap() 
	{
		sermonLookup = new HashMap<String, Sermon>();
		for (Sermon _sermon : sermons)
		{
			sermonLookup.put(_sermon.Title,_sermon);
		}
	}
	
	/*
	 * Set the download status based on the input received from downloader
	 */
	public void setDownloadStatus(Sermon sermon)
	{
		Sermon _sermon = sermonLookup.get(sermon.Title);
		int index = sermons.indexOf(_sermon);
		if (index != -1)
		{
			sermons.get(index).Status = sermon.Status;
			sermons.get(index).isSermonSelected = false;
			fireTableCellUpdated(index, 2);
		}
	}

	/*
	 * Returns all sermons Array List
	 */
	public ArrayList<Sermon> getSermons() 
	{
		return sermons;
	}

	/* 
	 * Returns the list of selected sermons
	 */
	public ArrayList<Sermon> getSelectedSermons() 
	{
		ArrayList<Sermon> selectedSermons = new ArrayList();
		for (Sermon _sermon:sermons)
		{
			if (_sermon.isSermonSelected) 
				selectedSermons.add(_sermon);
		}
		return selectedSermons;
	}
	
	/*
	 * Adds a Sermon to list of Sermons
	 */
	public void addRow(Sermon _sermon)
	{
		sermons.add(_sermon);
	}
	
	/*
	 * Clears the table
	 */
	
	/*
	 * Prepares the column information for the model
	 * Not being used (Deprecated) 
	 */
	private void CollectColumns()
	{
		columns = new ArrayList();
		for (Field f : properties)
		{
			TableMappings fieldAttributes = Utilities.MetaData(f); 
			if (fieldAttributes.Visible())
			{
				columns.add(fieldAttributes.Header());
			}
		}
		fireTableStructureChanged();
	}
}


