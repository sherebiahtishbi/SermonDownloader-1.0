package com.tishbi.sitecontent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SwingWorker;

import com.tishbi.sitecontent.AllEnums.UpdateStatus;
import com.tishbi.util.Globals;
import com.tishbi.util.Logger;
import com.tishbi.util.UnZipper;

public class UpdatesChecker
{
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	File existingFile;
	File[] updatedCachefiles;
	
	public void CheckforUpdates()
	{
//		SwingWorker updateWorker = new SwingWorker()
//		{
//			@Override
//			protected Object doInBackground() throws Exception
//			{
				String sfile = Globals.tempPath()+"sermonindexcacheupdate.zip";
				Logger.Log("Cache path : " + sfile);
				File file = new File(sfile);
				pcs.firePropertyChange("updateprogress",null,"Updates found.");
				Logger.Log("Copying Cache from : " + Globals.updateCheckUrl);
				try
				{
					org.apache.commons.io.FileUtils.copyURLToFile(new URL(Globals.updateCheckUrl), file);
					pcs.firePropertyChange("updateprogress",null,"Applying Updates.....");
					Logger.Log("Unpacking updates.");
				
					UnZipper.Unzip(file);
					
					File tempDir = new File(Globals.tempPath());
					//if (!tempDir.exists()) return UpdateStatus.UPDATE_FAILED;
					
					FileFilter filter = new FileFilter()
					{
						@Override
						public boolean accept(File f)
						{
							if (f.getName().endsWith("zip"))
								return true;
							else
								return false;
						}
					}; 
					
					updatedCachefiles = tempDir.listFiles(filter);
					
					//if (updatedCachefiles.length == 0)
						//return UpdateStatus.UPDATE_NOT_FOUND;
					
					boolean isSame=false;
					int counter=1;
					for (File updFile : updatedCachefiles)
					{
						existingFile = new File(Globals.cachePath()+updFile.getName());
						try
						{
							isSame = org.apache.commons.io.FileUtils.contentEquals(updFile, existingFile);
							if (!isSame)
							{
								org.apache.commons.io.FileUtils.copyFile(updFile, existingFile);
							}
						}
						catch (IOException ex)
						{
							ex.printStackTrace();
						}
						pcs.firePropertyChange("updateprogress",null,"Total " + counter++ + " speakers sermons available.");
					}
				}
				catch (MalformedURLException ex1)
				{
					ex1.printStackTrace();
				}
				catch (IOException ex1)
				{
					ex1.printStackTrace();
				}	
				pcs.firePropertyChange("updateprogress",null,"Updates successfully applied.");
//				return null;
//			}
//		};
//		updateWorker.execute();
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
