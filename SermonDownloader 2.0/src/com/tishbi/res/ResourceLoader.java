package com.tishbi.res;

import java.awt.Image;
import java.awt.Toolkit;

public class ResourceLoader 
{
	static ResourceLoader rl = new ResourceLoader();
	
	//General UI
	private static String downloadImage = "images/download.png";
	private static String openFolderImage = "images/openfolder.png";
	private static String searchImage = "images/find.png";
	private static String playingIndicatorImage = "images/playindicator.png";

	public static Image downloadImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(downloadImage));
	}

	public static Image openFolderImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(openFolderImage));
	}

	public static Image searchImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(searchImage));
	}

	public static Image playIndicatorImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(playingIndicatorImage));
	}

	//Speaker list & Sermon Table related images
	private static String playEnabledImage = "images/playenabled.png";
	private static String playDisabledImage = "images/playdisabled.png";
	private static String pauseImage = "images/pause.png";
	private static String speakerDefaultImage = "images/noimage.png";
	private static String syncupImage = "images/syncup.png";
	private static String appImage = "images/appicon.png";
	
	public static Image playEnableButton()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(playEnabledImage));
	}
	
	public static Image playDisableButton()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(playDisabledImage));
	}
	
	public static Image pauseButton()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(pauseImage));
	}
	
	public static Image speakerDefaultImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(speakerDefaultImage));
	}

	public static Image syncupImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(syncupImage));
	}
	
	public static Image AppImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(appImage));
	}
	
	//Toolbar related images
	private static String toolbarGotoFirstImage = "images/toolbarfirst.png";
	private static String toolbarGotoPrevImage = "images/toolbarprev.png";
	private static String toolbarPlayImage = "images/toolbarplay.png";
	private static String toolbarPauseImage = "images/toolbarpause.png";
	private static String toolbarGotoNextImage = "images/toolbarnext.png";
	private static String toolbarGotoLastImage = "images/toolbarlast.png";
	
	public static Image toolbarGotoFirstImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(toolbarGotoFirstImage));
	}
	
	public static Image toolbarGotoPreviousImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(toolbarGotoPrevImage));
	}

	public static Image toolbarPlayImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(toolbarPlayImage));
	}
	
	public static Image toolbarPauseImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(toolbarPauseImage));
	}

	public static Image toolbarGotoNextImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(toolbarGotoNextImage));
	}
	
	public static Image toolbarGotoLastImage()
	{
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(toolbarGotoLastImage));
	}
}
