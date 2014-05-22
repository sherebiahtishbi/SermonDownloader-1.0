package com.tishbi.ui;

import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.tishbi.sitecontent.Speaker;
import com.tishbi.util.Logger;

public class SearchListener implements DocumentListener
{
	private DefaultListModel<Speaker> model = null;
	
	public SearchListener(DefaultListModel<Speaker> _model)
	{
		this.model = _model;
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		Document doc = e.getDocument();
		try
		{
			System.out.println("Change :" + doc.getText(0, doc.getLength()));
		}
		catch (BadLocationException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		Document doc = e.getDocument();
		try
		{
			System.out.println("Insert : " + doc.getText(0, doc.getLength()));
		}
		catch (BadLocationException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		Document doc = e.getDocument();
		try
		{
			System.out.println("Remove : " + doc.getText(0, doc.getLength()));
		}
		catch (BadLocationException ex)
		{
			ex.printStackTrace();
		}
	}
}
