package com.tishbi.sitecontent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.util.Globals;
import com.tishbi.util.Logger;
import com.tishbi.util.Utilities;

public class Downloader 
{
	private static ArrayList<String> allowedContentTypes = new ArrayList<String>();
	
	public static DownloadStatus Download(Sermon _sermon) throws MalformedURLException, IOException
	{
		Logger.Log("Downloading " + _sermon.Title);
		return Download(_sermon.Url, _sermon.Title, _sermon.Speaker);
	}
	
	public static DownloadStatus Download(String Url, String filename) throws MalformedURLException, IOException
	{
		return Download(Url,filename,null);
	}

	public static DownloadStatus Download(String url, String filename, String speakerName) throws MalformedURLException, IOException
	{
        filename = Utilities.getSermonFilename(filename,speakerName);
        File file = new File(filename);
        if (file.exists()) return DownloadStatus.ALREADY_DONE; 

		allowedContentTypes.add("audio/mpeg3");
		allowedContentTypes.add("audio/x-mpeg-3");
		allowedContentTypes.add("video/mpeg");
		allowedContentTypes.add("video/x-mpeg");

		DownloadStatus retVal = DownloadStatus.NOT_STARTED;
        try
        {
        	org.apache.commons.io.FileUtils.copyURLToFile(new URL(url), file);
        	retVal = DownloadStatus.COMPLETED;
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	Logger.Log("Download(String url, String filename, String speakerName)",ex);
        }
        return retVal;
	}
}
