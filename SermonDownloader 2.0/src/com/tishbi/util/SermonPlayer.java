package com.tishbi.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Thread.State;
import java.util.ArrayList;

import com.tishbi.sitecontent.Sermon;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SermonPlayer extends Thread
{
	private static Player player = null;
	private static String filename;
	private static FileInputStream inputStream;
	private ArrayList<Sermon> playList;
	private Sermon currentSermon;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public SermonPlayer(Sermon _sermon)
	{
		this.currentSermon = _sermon;
		//pcs.firePropertyChange("player", null, _sermon);
	}
	
	@Override
	public void run() 
	{
		try 
		{
			filename = Utilities.getSermonFilename(currentSermon.Title,currentSermon.Speaker);
			inputStream = new FileInputStream(new File(filename));
			player = new Player(inputStream);
			player.play();
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		} 
		catch (JavaLayerException e) 
		{
			e.printStackTrace();
		}	
	}
	
	public void Stop()
	{
		if (player != null)
			player.close();
		interrupt();
	}
	
	public Sermon getCurrentSermon()
	{
		return this.currentSermon;
	}
	
	public boolean trackComplete()
	{
		if (player != null)
			return player.isComplete();
		else
			return false;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
	}
}
