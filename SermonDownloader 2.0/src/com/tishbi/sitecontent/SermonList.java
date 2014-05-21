package com.tishbi.sitecontent;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Sermons")
@XmlAccessorType(XmlAccessType.FIELD)
public class SermonList 
{
	@XmlElement(name="Sermon")
	private ArrayList<Sermon> sermonList = null;

	public ArrayList<Sermon> getSermonlist() 
	{
		return sermonList;
	}

	public void setSermonlist(ArrayList<Sermon> sermonlist) 
	{
		this.sermonList = sermonlist;
	}
}
