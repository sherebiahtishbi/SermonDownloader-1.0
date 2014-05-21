package com.tishbi.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import com.tishbi.sitecontent.Sermon;
import com.tishbi.sitecontent.SermonIndexCache;

public class PlaybackListener implements PropertyChangeListener
{
	private Sermon currentSermon=null;
	private JLabel lblToUpdate;
	
	public PlaybackListener(JLabel lbl)
	{
		this.lblToUpdate = lbl;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e)
	{
		if (e.getPropertyName() == "currentplayback")
		{
			currentSermon = (Sermon) e.getNewValue();
			if (currentSermon == null)
			{
				this.lblToUpdate.setText("");
				this.lblToUpdate.setVisible(false);
				return;
			}
			this.lblToUpdate.setVisible(true);
			this.lblToUpdate.setText(currentSermon.Title);
			this.lblToUpdate.setIcon(SermonIndexCache.playIndicatorImage());
		}
	}
}
