package com.tishbi.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.SwingWorker;

import com.tishbi.sitecontent.AllEnums.DownloadStatus;
import com.tishbi.sitecontent.Sermon;
import com.tishbi.ui.SermonTableModel;

public class Playlist
{
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private ArrayList<Sermon> playList=null;
	private JTable sermonTable;
	private boolean playerStopped = false;
	private SermonPlayer player;
	private int playIndex=-1;
	
	
	public Playlist(ArrayList<Sermon> _playlist, JTable _sermonTable)
	{
		this.playList = _playlist;
		this.sermonTable = _sermonTable;
	}
	
	public Playlist(JTable _sermonTable)
	{
		this.sermonTable = _sermonTable;
	}
	
	public void Play()
	{
		if (this.playList == null || this.playList.isEmpty())
		{
			Utilities.ShowMessage("No sermon or song selected to play.", "Empty playlist");
			return;
		}
		
		SwingWorker musicWorker = new SwingWorker()
		{
			@Override
			protected Date doInBackground() throws Exception 
			{
				playerStopped = false;
				Sermon _sermon;
				if (playIndex == -1) playIndex=0;
				Logger.Log("Before entering loop current playindex :" + playIndex);
				StopCurrentTrack();
				for(int i = playIndex; i < playList.size(); i++)
				{
					_sermon = playList.get(i);
					if (playerStopped) break;
					Logger.Log("In loop, Current playindex : " + playIndex + ", loop counter : " + i);
					_sermon.Status = DownloadStatus.PLAYING;
					((SermonTableModel)sermonTable.getModel()).setDownloadStatus(_sermon);
					sermonTable.repaint();
					player = new SermonPlayer(_sermon);
					Logger.Log("Now playing : "+_sermon.Title);
					pcs.firePropertyChange("currentplayback",null, _sermon);
					try 
					{
						player.sleep(1000);
					} 
					catch (InterruptedException e1) 
					{
						e1.printStackTrace();
					}
					player.start();
					player.join();
				}
				return new Date();
			}

			@Override
			protected void done()
			{
				Stop();
			}
			
		};
		musicWorker.execute();
	}
	
	public void Add(Sermon _sermon)
	{
		if (playList == null)
			playList = new ArrayList<Sermon>();
		
		playList.add(_sermon);
	}
	
	public Sermon currentlyPlaying()
	{
		if (player!=null) 
			return player.getCurrentSermon();
		else
			return null;
	}
	
	public int trackCount()
	{
		if (playList.isEmpty())
			return 0;
		else
			return playList.size();
	}
	
	public void Stop()
	{
		if (player == null) return;
		StopCurrentTrack();
		playerStopped = true;
		player = null;
	}

	public void First()
	{
		if (playList == null || playIndex == -1) return;
		playIndex=0;
		Play();
	}
	
	public void Previous()
	{
		if (playList == null || playIndex == -1) return;
		--playIndex;
		Play();
	}

	public void Next()
	{
		System.out.println("Next button Pressed");
		if (playList == null || playIndex == -1) return;
		StopCurrentTrack();
		++playIndex;
		Play();
	}
	
	public void Last()
	{
		if (playList == null || playIndex == -1) return;
		playIndex = playList.size()-1;
		Play();
	}
	
	private void playtrack()
	{
		if (playList == null || playIndex == -1) return;
		
	}
	
	private void StopCurrentTrack()
	{
		if (player != null)
		{
			Sermon currSermon = player.getCurrentSermon();
			player.Stop();
			Logger.Log("Stopping current track : " + currSermon.Title);
			pcs.firePropertyChange("currentplayback",null, null);
			currSermon.Status = DownloadStatus.ALREADY_DONE;
			((SermonTableModel)sermonTable.getModel()).setDownloadStatus(currSermon);
			sermonTable.repaint();
		}
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
