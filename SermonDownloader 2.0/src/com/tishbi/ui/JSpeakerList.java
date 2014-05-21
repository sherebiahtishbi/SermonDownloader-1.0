package com.tishbi.ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import com.tishbi.sitecontent.Speaker;
import com.tishbi.util.Globals;


public class JSpeakerList extends JList 
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Speaker> speakers;
	private DefaultListModel<Speaker> speakerListModel;
	
	public JSpeakerList()
	{
		
	}

	public JSpeakerList(ArrayList<Speaker> _speakers)
	{
		if (_speakers.isEmpty()) return;
		
		this.speakers = _speakers;
		setOpaque(true);
		//setCellRenderer(new SpeakerListRenderer());

		setListModel();
		addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e)
			{
				int index = locationToIndex(e.getPoint());
				if (index != -1)
				{
					//SpeakerListItem pnlSpeaker = (SpeakerListItem) getModel().getElementAt(index);
					Speaker _speaker = (Speaker) getModel().getElementAt(index);
					setSelectedIndex(index);
					repaint();
				}
			}
		});
		
		addMouseMotionListener(new MouseAdapter() 
		{
			public void mouseMoved(MouseEvent e)
			{
				int index = locationToIndex(e.getPoint());
				if (index != -1)
				{
					//SpeakerListItem pnlSpeaker = (SpeakerListItem) getModel().getElementAt(index);
					Speaker _speaker = (Speaker) getModel().getElementAt(index); 
					setSelectedIndex(index);
					repaint();
				}
			}
		});
	}
	
	public Integer Size()
	{
		return this.speakers.size();
	}
	
	public Speaker getSpeakerAt(Integer index)
	{
		if (index <= this.speakers.size())
			return this.speakers.get(index);
		else
			return null;
	}
	
	public void setSpeakerAt(Speaker speaker, Integer index)
	{
		if (index <= this.speakers.size() && speaker != null)
		{
			this.speakers.remove(index);
			this.speakers.add(index,speaker);
		}
	}
	
	private void setListModel()
	{
		if (this.speakers.size()>0)
		{
			speakerListModel = new DefaultListModel<Speaker>();
			for (Speaker speaker : speakers)
			{
				speakerListModel.addElement(speaker);
			}
			setModel(speakerListModel);
		}
	}
	
	private void setFilter(String filterText)
	{
		if (filterText.trim().length() == 0)
		{
			if (speakerListModel.size() != speakers.size())
			{
				setListModel();
			}
			return;
		}
		speakerListModel.clear();
		for(Speaker spk : speakers)
		{
			if (spk.Name.toUpperCase().contains(filterText.toUpperCase()))
			{
				speakerListModel.addElement(spk);
			}
		}
		setModel(speakerListModel);
	}
	
	


}
