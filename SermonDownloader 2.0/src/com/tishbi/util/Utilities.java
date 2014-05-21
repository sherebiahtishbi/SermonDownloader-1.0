package com.tishbi.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.tishbi.sitecontent.AllEnums.OS;
import com.tishbi.sitecontent.Sermon;
import com.tishbi.sitecontent.TableMappings;

public class Utilities 
{
	//Checks if Url is a valid one
	public static boolean UrlExists(String _url) throws IOException
	{
		URL url = new URL(_url);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		int responseCode = huc.getResponseCode();	
		return (responseCode != 404) ? true : false; 
	}
	
	public static String ContentLength(String _url) throws IOException
	{
		String retVal = null;
		try
		{
			if (_url == null || _url.trim().length() == 0) return "";
			URL url = new URL(_url);
			String MbKb = "Kb";
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			double interim = (int) huc.getContentLengthLong()/1024;
			if (interim > 1024)
			{
				interim = interim / 1024;
				MbKb = "Mb";
			}
			DecimalFormat sizeFormatter = new DecimalFormat("###.##");
			retVal = sizeFormatter.format(interim) + MbKb;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return retVal;
	}
	
	//Returns elapsed time in HH:MM:SS format
    public static String TimeTaken(Date start)
    {
        Date end = new Date(); 
        long millisec = end.getTime()-start.getTime();
        long hours, minutes, seconds;
        seconds = millisec / 1000;         
        minutes = millisec / (60 * 1000);         
        hours = millisec / (60 * 60 * 1000); 
        String h = String.valueOf(hours).trim();
        String m = String.valueOf(minutes).trim();
        String s = String.valueOf(seconds).trim();
        
        h = h.length() < 2 ? "0"+h:h.substring(0,2);
        m = m.length() < 2 ? "0"+m:m.substring(0,2);
        s = s.length() < 2 ? "0"+s:s.substring(0,2);
        
        return h+":"+m+":"+s;
    }	
    
    //Returns time stamp
    public static String TimeStamp()
    {
    	SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");		
		return format.format(new Date());
    }
    
    //Returns time stamp
    public static String FullTimeStamp()
    {
    	SimpleDateFormat format = new SimpleDateFormat("MM dd yyyy hh:mm:ss aa - ");		
		return format.format(new Date());
    }
    
    public static String logTimeStamp()
    {
    	Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yyyy hh:mm:ss aa");	
		return format.format(d) + " -> ";
    }
    
    
	public static TableMappings MetaData(Field field)
	{
		//check field annotations 
		Annotation[] annotations = field.getDeclaredAnnotations();
		for(Annotation annotation : annotations)
		{
		    if(annotation instanceof TableMappings)
		    	return (TableMappings) annotation;
		}			
		return null;
	}
	
	public static void ShowMessage(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int Confirm(String message, String Title)
	{
		return JOptionPane.showConfirmDialog(null, message, Title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	public static boolean Exists(Sermon sermon, String speakerName)
	{
		Logger.Log("Utitlies.Exist() -> Preparing sermon file name.");
		String filename = getSermonFilename(sermon.Title,speakerName);
		Logger.Log("Utitlies.Exist() -> File name retrieved : " + filename);
		File sermonFile = new File(filename);
		return sermonFile.exists();
	}
	
	public static String getSermonFilename(String sermonTitle, String speakerName)
	{
		if (Globals.os == OS.WINDOWS)
			return Globals.sermonPath()+getFormatedSpeakerName(speakerName)+"\\"+sermonTitle.replaceAll("[():!?@#$%^&* ]*", "")+".mp3";
		else
			return Globals.sermonPath()+getFormatedSpeakerName(speakerName)+"/"+sermonTitle.replaceAll("[():!?@#$%^&* ]*", "")+".mp3";
	}
	
	public static String getFormatedSpeakerName(String speakerName)
	{
		return speakerName.replaceAll("[():!?@#$%^&*. ]*", "");
	}
	
}
