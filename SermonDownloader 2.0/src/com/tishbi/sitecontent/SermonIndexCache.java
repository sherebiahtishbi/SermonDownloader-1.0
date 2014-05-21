package com.tishbi.sitecontent;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.text.Utilities;

import com.tishbi.res.ResourceLoader;
import com.tishbi.util.Globals;
import com.tishbi.util.XmlUtility;

public class SermonIndexCache 
{
	public static boolean isLocalCacheOfSpeakersAvailable()
	{
		File speakerXmlFile = new File(Globals.speakerCatalogLocation());
		return speakerXmlFile.exists();
	}
	
	public static ArrayList<Speaker> getSpeakersFromCache()
	{
		return XmlUtility.SpeakersFromXml();
	}
	
	public static boolean isLocalCacheOfSermonsForSpeakerAvailable(String speakerName)
	{
		File sermonFile = new File(Globals.cachePath() + com.tishbi.util.Utilities.getFormatedSpeakerName(speakerName) +  ".xml");  
		return sermonFile.exists();
	}
	
	public static ArrayList<Sermon> getSermonsOfSpeaker(Speaker _speaker)
	{
		return XmlUtility.SermonsFromXml(_speaker);
	}
	
	public static void updateSermonCache(Speaker speaker)
	{
		XmlUtility.SermonsToXml(speaker);
	}
	
	public static void updateSpeakersCache(ArrayList<Speaker> speakerList)
	{
		XmlUtility.SpeakersToXml(speakerList);
	}
	
	//All Images
	public static Image getPlayButton(boolean enabled)
	{
		if (enabled)
			return ResourceLoader.playEnableButton().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
		else
			return ResourceLoader.playDisableButton().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
	}
	
	public static Image getPauseButton()
	{
		return ResourceLoader.pauseButton().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
	}
	
	public static Image getSyncupImage()
	{
		return ResourceLoader.syncupImage().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
	}
	
	public static Image speakerDefaultImage()
	{
		return ResourceLoader.speakerDefaultImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
	}
	
	public static Image AppImage()
	{
		return ResourceLoader.AppImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
	}
	
	public static ImageIcon downloadButtonImage()
	{
		return new ImageIcon(ResourceLoader.downloadImage().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH));
	}

	public static ImageIcon openFolderButtonImage()
	{
		return new ImageIcon(ResourceLoader.openFolderImage().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH));
	}
	
	public static ImageIcon searchButtonImage()
	{
		return new ImageIcon(ResourceLoader.searchImage().getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH));
	}

	public static ImageIcon playIndicatorImage()
	{
		return new ImageIcon(ResourceLoader.playIndicatorImage().getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH));
	}

	//toolbar images
	public static ImageIcon toolbarGotoFirstImage()
	{
		return new ImageIcon(ResourceLoader.toolbarGotoFirstImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	}
	
	public static ImageIcon toolbarGotoNextImage()
	{
		return new ImageIcon(ResourceLoader.toolbarGotoNextImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	}

	public static ImageIcon toolbarPlayImage()
	{
		return new ImageIcon(ResourceLoader.toolbarPlayImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	}
	
	public static ImageIcon toolbarStopImage()
	{
		return new ImageIcon(ResourceLoader.toolbarStopImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	}

	public static ImageIcon toolbarPauseImage()
	{
		return new ImageIcon(ResourceLoader.toolbarPauseImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	}
	
	public static ImageIcon toolbarGotoPreviousImage()
	{
		return new ImageIcon(ResourceLoader.toolbarGotoPreviousImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	}
	
	public static ImageIcon toolbarGotoLastImage()
	{
		return new ImageIcon(ResourceLoader.toolbarGotoLastImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	}
}
