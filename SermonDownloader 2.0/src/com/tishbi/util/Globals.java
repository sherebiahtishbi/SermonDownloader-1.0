package com.tishbi.util;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.tishbi.res.ResourceLoader;
import com.tishbi.sitecontent.AllEnums.*;

public class Globals 
{
	private static String userHome = System.getProperty("user.home");
	public static Border unselectedBorder = new LineBorder(Color.white, 2, true);
	public static Border selectedBorder = new LineBorder(new Color(140, 140, 90), 2, true);
	public static String validSermonUrl = "http://www.sermonindex.net/modules/mydownloads/visit.php";
	public static String speakerBaseUrl = "http://www.sermonindex.net/modules/mydownloads/viewcat.php?cid=";
	public static String updateCheckUrl = "http://img.sermonindex.net/Cache_Update.zip";
	public static String sermonSecondUrl = "&min=20&orderby=titleA&show=1000";
	public static String hrefExtractor = "abs:href";

	public static OS os;
	
	public static String getOS()
	{
		String OS = System.getProperty("os.name").toLowerCase();
		String OSVersion = System.getProperty("os.version");
		String OSArchitecture = System.getProperty("os.arch");

		if (OS.indexOf("win") >= 0)
		{
			os = com.tishbi.sitecontent.AllEnums.OS.WINDOWS;
			return "Windows " + OSVersion + " " + OSArchitecture;
		}
		if (OS.indexOf("mac") >= 0)
		{
			os = com.tishbi.sitecontent.AllEnums.OS.MAC;
			return "Mac OS " + OSVersion + " " + OSArchitecture;
		}
		if (OS.indexOf("sunos") >= 0)
		{
			os = com.tishbi.sitecontent.AllEnums.OS.SOLARIS;
			return "Sun Solaris " + OSVersion + " " + OSArchitecture;
		}
		if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") >= 0)
		{
			os =com.tishbi.sitecontent.AllEnums.OS.UNIX_OR_LINUX;
			return "Unix/Linux " + OSVersion + " " + OSArchitecture;
		}
		os = com.tishbi.sitecontent.AllEnums.OS.UNKNOWN;
		return "Unknown";
	}
	
	public static String cachePath()
	{
		if (os == OS.WINDOWS)
			return userHome+"\\SermonIndex_Cache\\";
		else
			return userHome+"/SermonIndex_Cache/";
	}

	public static String sermonPath()
	{
		if (os == OS.WINDOWS)
			return userHome+"\\SermonIndex_Sermons\\";
		else
			return userHome+"/SermonIndex_Sermons/";
	}
	
	public static String logPath()
	{
		if (os == OS.WINDOWS)
			return userHome+"\\SermonIndex_AppLog\\";
		else
			return userHome+"/SermonIndex_AppLog/";
	}

	public static String tempPath()
	{
		if (os == OS.WINDOWS)
			return cachePath()+"temp\\";
		else
			return cachePath()+"temp/";
	}
	
	public static String sermonLinkPattern()
	{
		return "a[href*=" + Globals.validSermonUrl + "]";
	}

	public static String speakerCatalogLocation()
	{
		return cachePath()+"speakers.xml";
	}
}
