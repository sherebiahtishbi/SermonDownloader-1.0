package com.tishbi.sitecontent;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;

public class Speaker
{
	public String Name;
	public String MainUrl;
	public String AdditionalUrl;
	public ArrayList<Sermon> Sermons;
	public String Description;
	public Image SpeakerImage;
	public boolean isSelected;
	public boolean localCacheExist;
	
	public Speaker()
	{
		
	}
	
	public Speaker(String _name, String _mainUrl, Image _image)
	{
		this.Name = _name;
		this.MainUrl = _mainUrl;
		this.SpeakerImage = _image;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (obj instanceof Speaker)
		{
			if (this.Name == ((Speaker) obj).Name)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
