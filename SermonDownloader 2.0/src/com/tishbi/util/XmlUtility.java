package com.tishbi.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.sitecontent.Sermon;
import com.tishbi.sitecontent.SermonList;
import com.tishbi.sitecontent.Speaker;
import com.tishbi.sitecontent.SpeakerList;

public class XmlUtility 
{
	//Converts speakers collection to XML
	public static void SpeakersToXml(ArrayList<Speaker> _speakers)
	{
		try 
		{
				SpeakerList spks = new SpeakerList();
				spks.setSpeakerList(_speakers);
				JAXBContext jaxbContext = JAXBContext.newInstance(SpeakerList.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				 
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				     
				jaxbMarshaller.marshal(spks, new File(Globals.speakerCatalogLocation()));	
		} 
		catch (PropertyException e) {
			e.printStackTrace();
		} 
		catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	//Creates speakers collection from XML
	public static ArrayList<Speaker> SpeakersFromXml()
	{
		ArrayList<Speaker> _speakers = null;
		try
		{
		JAXBContext jaxbContext = JAXBContext.newInstance(SpeakerList.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	     
	    SpeakerList spks = (SpeakerList) jaxbUnmarshaller.unmarshal(new File(Globals.speakerCatalogLocation()));
	    _speakers = spks.getSpeakerList();
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
		
		//Return Sorted list
		Collections.sort(_speakers, new Comparator<Speaker>() 
		{
			@Override
			public int compare(Speaker sp1, Speaker sp2) 
			{
				return sp1.Name.compareToIgnoreCase(sp2.Name);
			}
		});

		return _speakers;
	}
	
	public static void SermonsToXml(Speaker speaker)
	{
		SermonList sermonList = new SermonList();
		sermonList.setSermonlist(speaker.Sermons);
		JAXBContext jaxbContext;
		try 
		{
			jaxbContext = JAXBContext.newInstance(SermonList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			String fileToWrite = Globals.cachePath() + Utilities.getFormatedSpeakerName(speaker.Name) +  ".xml";
			Logger.Log("Writing to file : " + fileToWrite);
			     
			jaxbMarshaller.marshal(sermonList, new File(fileToWrite));	
			
		} 
		catch (JAXBException e) 
		{
			Logger.Log("SermonsToXml()", e);
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Sermon> SermonsFromXml(Speaker speaker)
	{
		ArrayList<Sermon> sermonList = null;
		if (speaker != null)
		{
			File sermonFile = new File(Globals.cachePath() + Utilities.getFormatedSpeakerName(speaker.Name) +  ".xml");
			if (!sermonFile.exists()) return null;
			try
			{
				JAXBContext jaxbContext = JAXBContext.newInstance(SermonList.class);
			    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			     
			    SermonList lst = (SermonList) jaxbUnmarshaller.unmarshal(sermonFile);
			    sermonList = lst.getSermonlist();
			    for (Sermon _sermon : sermonList)
			    {
			    	_sermon.Status = Utilities.Exists(_sermon, speaker.Name) ? DownloadStatus.ALREADY_DONE : DownloadStatus.NOT_STARTED;
			    }
			}
			catch(JAXBException e)
			{
				e.printStackTrace();
			}
			
			Collections.sort(sermonList, new Comparator<Sermon>() 
			{
				@Override
				public int compare(Sermon sermon1, Sermon sermon2) 
				{
					return sermon1.Title.compareToIgnoreCase(sermon2.Title);
				}				
			});
		}
		return sermonList;
	}
}
