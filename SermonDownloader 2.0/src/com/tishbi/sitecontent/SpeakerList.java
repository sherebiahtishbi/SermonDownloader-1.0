package com.tishbi.sitecontent;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Spekaers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpeakerList 
{
	@XmlElement(name="Speaker")
	private ArrayList<Speaker> speakerList = null;

	public ArrayList<Speaker> getSpeakerList() 
	{
		return speakerList;
	}

	public void setSpeakerList(ArrayList<Speaker> speakerList) 
	{
		this.speakerList = speakerList;
	}
}
