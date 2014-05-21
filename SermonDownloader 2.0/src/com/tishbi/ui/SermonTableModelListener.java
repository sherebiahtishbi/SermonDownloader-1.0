package com.tishbi.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTable;

import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.sitecontent.Sermon;

public class SermonTableModelListener implements PropertyChangeListener
{
	private SermonTableModel model;
	private JTable tbl; 
	
	public SermonTableModelListener(JTable _tbl)
	{
		this.tbl = tbl;
		this.model = ((SermonTableModel) tbl.getModel());
	}

	@Override
	public void propertyChange(PropertyChangeEvent e)
	{
		Sermon _sermon;
		if (e.getPropertyName() == "player")
		{
			for (int i=0;i<model.getRowCount();i++)
			{
				_sermon = (Sermon) model.getValueAt(i);
				_sermon.Status = DownloadStatus.ALREADY_DONE;
				model.setDownloadStatus(_sermon);
			}
			_sermon = (Sermon) e.getNewValue();
			_sermon.Status = DownloadStatus.PLAYING;
			model.setDownloadStatus(_sermon);
			tbl.repaint();
		}
	}
}
