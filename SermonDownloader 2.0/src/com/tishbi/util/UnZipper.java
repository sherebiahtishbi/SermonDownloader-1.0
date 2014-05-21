package com.tishbi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipper
{
	private static byte[] buffer = new byte[1024];

	public static void Unzip(File zipFile)
	{
		String fileName;
		File newFile;
		File cacheFolder;
		FileOutputStream fos;
		try
		{
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
    	ZipEntry ze = zis.getNextEntry();
	 
    	while(ze!=null)
    	{
    	   fileName = ze.getName();
           newFile = new File(Globals.cachePath() + fileName);
           if (newFile.isDirectory() || newFile.getName().startsWith(".")) 
           {
        	   ze = zis.getNextEntry();
        	   continue;
           }
           if (!(newFile.getName().toUpperCase().endsWith("XML"))) 
           {
        	   ze = zis.getNextEntry();
        	   continue;
           }
           
           new File(newFile.getParent()).mkdirs();
           
           cacheFolder = new File(newFile.getParent());
           if (!cacheFolder.exists())
           {
        	   Logger.Log("Creating Cache folder : " + newFile.getParent());
        	   cacheFolder.mkdirs();
           }
           fos = new FileOutputStream(newFile);             

           int len;
           while ((len = zis.read(buffer)) > 0) 
           {
        	   fos.write(buffer, 0, len);
           }

           fos.close();
           Logger.Log("Updates applied : "+ newFile.getAbsoluteFile());
           ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();	
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
