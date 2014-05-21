package com.tishbi.sitecontent;

import java.awt.Image;
import java.io.IOException;

import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.util.Logger;
import com.tishbi.util.Utilities;

public class Sermon 
{
	@TableMappings(Header="Title", Visible= true)
	public String Title;
	
	@TableMappings(Header="Web Link", Visible= false)
	public String Url;
	
	@TableMappings(Header="Speaker", Visible= false)
	public String Speaker;
	
	@TableMappings(Header="Size",Visible=true)
	public String Length;
	
	@TableMappings(Header="Description",Visible=false)
	public String Description = null;
	
	@TableMappings(Header="Status",Visible=true)
	public DownloadStatus Status = DownloadStatus.NOT_STARTED;
	
	@TableMappings(Header="Download?",Visible=true)
	public boolean isSermonSelected = false;
	
	@TableMappings(Header="Play",Visible=true)
	public Image icon;

	public Sermon(){}
	
	public Sermon(String url, String title) throws IOException
	{
		try
		{
			this.Title = title;
			this.Url = url;	
			this.Length = Utilities.ContentLength(url);
		}
		catch(Exception ex)
		{
			Logger.Log("Error Occurred creating Sermon object : " + ex.toString());
			ex.printStackTrace();
		}
	}
	
	public String getStatus()
	{
		String _status;
		switch(Status)
		{
			case NOT_STARTED:
				_status = "Download"; break;
			case DOWNLOADING:
				_status = "Downloading.."; break;
			case ALREADY_DONE:
				_status = "Already Exists"; break;
			case COMPLETED:
				_status = "Complete"; break;
			case FAILED:
				_status = "Failed"; break;
			default: 
				_status = "Unknown"; break;
		 }		
		return _status;
	}

	@Override
	public String toString() 
	{
		if (this.Title != null)
			return this.Title;
		else
			return "";
	}

	@Override
	public boolean equals(Object sermon) 
	{
		if (sermon instanceof Sermon)
		{
			if (this.Title == ((Sermon) sermon).Title)
				return true;
			else
				return false;
		}
		else
			return false;
			
	}
	
	
}
