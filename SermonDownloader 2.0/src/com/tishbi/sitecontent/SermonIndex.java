package com.tishbi.sitecontent;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.util.Globals;
import com.tishbi.util.Logger;
import com.tishbi.util.Utilities;

public class SermonIndex 
{
	private static ArrayList<Speaker> outSpeakers = new ArrayList<Speaker>();
	private static ArrayList<Sermon> sermons = new ArrayList<Sermon>();
	
	public static void GetSermonsByUrl(String url, String speakerName)
    {
		Logger.Log("GetSermonsByUrl-> " + url + "," + speakerName);
    	ArrayList<Sermon> searchResult = new ArrayList<Sermon>();
        try
        {
        	Logger.Log("GetSermonsByUrl-> Retrieving HTML");
            Document doc = Jsoup.connect(url).get();
            Logger.Log("GetSermonsByUrl-> HTML retrieved successfully, now parsing HTML");
            Elements links = doc.select("a[href]");
            Sermon _sermon = null;
            Logger.Log("GetSermonsByUrl-> Sermon links collected.");
            for (Element link : links) 
            {
            	Logger.Log("GetSermonsByUrl-> ==============");
            	Logger.Log("GetSermonsByUrl-> Checking Valididty of sermon url : " + link.attr("abs:href").toString());
                if (link.attr("abs:href").contains(Globals.validSermonUrl))
                {
                	Logger.Log("GetSermonsByUrl-> Url is valid sermon url. Adding new Sermon object.");
                    _sermon = new Sermon(link.attr("abs:href"),link.text());
                    Logger.Log("GetSermonsByUrl-> New sermon object created. Checking local existence. [" + _sermon.Title + "]");
                    _sermon.Status = Utilities.Exists(_sermon, speakerName) ? DownloadStatus.ALREADY_DONE : DownloadStatus.NOT_STARTED;
                    _sermon.Speaker = speakerName;
                    sermons.add(_sermon);
                    Logger.Log("GetSermonsByUrl-> Sermon added. - " + _sermon.Title);
                }
            }
        }
        catch(IOException ioexp)
        {
        	ioexp.printStackTrace();
        	Logger.Log(ioexp.toString());
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	Logger.Log(ex.toString());
        }
    }
    
    //Collects all sermon by a given speaker
    public static ArrayList<Sermon> Sermons(Speaker speaker)
    {
    	if (speaker == null || speaker.MainUrl.trim().isEmpty()) return null;
    	sermons.clear();
    	Logger.Log("public static ArrayList<Sermon> Sermons(Speaker speaker) "+speaker.Name);
    	String secondUrl;
        Logger.Log("Fetching sermons for initial URL");
		GetSermonsByUrl(speaker.MainUrl, speaker.Name);
		Logger.Log("Completed fetching sermons for initial URL, now fetching for 2nd URL");
		secondUrl  = speaker.MainUrl+"&min=20&orderby=titleA&show=1000";
		GetSermonsByUrl(secondUrl, speaker.Name);
		Logger.Log("Completed fetching sermons for 2nd URL.");
        if (sermons.size()>0)
        {
        	speaker.Sermons = sermons;
        }
        return sermons;
    }
	
	//Retrieves all speakers
	//Internal method  
	private static void getSpeakers() throws IOException
	{
		Logger.Log("Completed fetching sermons for 2nd URL.");
		outSpeakers.clear();
		for (int i=1;i<=20;i++)
		{
			outSpeakers.add(getSpeaker(Globals.speakerBaseUrl+ i));
		}
	}
	
	//Creates and returns Speaker object from a given url
	public static Speaker getSpeaker(String url) throws IOException
	{
		Logger.Log("public static Speaker getSpeaker(String url) throws IOException - "+url);
		Speaker _speaker = null;
		String title;
		Image _image = null;
		if (Utilities.UrlExists(url))
		{
			Logger.Log("Searching for speaker data ");
			Document doc = Jsoup.connect(url).get();
			title = getTitle(doc);
			if (title.contains("Audio") || title.contains("SermonIndex"))
				return null;
			else
			{
				Logger.Log("Searching for speaker image ");
				_image = getImage(doc, title);
				_speaker = new Speaker(title,url,_image);
			}
		}
		Logger.Log("Collected speaker meta data " + _speaker.Name);
		return _speaker;
	}
	
	//Retrieves all speakers 
	//Exposed method to be consumed by client
	public static ArrayList<Speaker> Speakers() throws IOException
	{
		try
		{
			getSpeakers();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return outSpeakers;
	}
	
	//Gets the content of Title tag from a given url
	private static String getTitle(Document _doc)
	{
		String title = _doc.select("title").first().toString();
		title = title.replaceFirst("Audio Sermons - Sermon Index", "");
		title = title.replaceFirst("<title>", "");
		title = title.replaceFirst("</title>", "");
		return title.trim();
	}
	
	//Gets the image from a given url and returns as Image object
	private static Image getImage(Document _doc, String _title) throws IOException
	{
		String _imgurl = null;

		Elements imglinks = _doc.getElementsByTag("img"); 
		for(Element e : imglinks)
		{
			if (e.attr("alt").equalsIgnoreCase(_title))
			{
				_imgurl = e.attr("src");
				break;
			}
		}
		if (_imgurl != null)
		{
			URL iconURL = new URL(_imgurl);
	        ImageIcon icon = new ImageIcon(iconURL);
	        return icon.getImage();
		}
		else
			return null;
	}
}
