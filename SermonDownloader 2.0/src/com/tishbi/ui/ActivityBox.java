package com.tishbi.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

import com.tishbi.util.Logger;
import com.tishbi.util.Utilities;

public class ActivityBox extends JTextArea 
{
	private static final long serialVersionUID = 1L;

	@Override
	public void append(String text) 
	{
		String finalText = Utilities.logTimeStamp()+ text;
		super.append(finalText);
		Logger.Log(text);
	}
	
	public void append(Exception ex)
	{
		String finalText = Utilities.logTimeStamp()+ ex.toString();
		super.append(finalText);
		Logger.Log(ex.toString());
		
	}
}
