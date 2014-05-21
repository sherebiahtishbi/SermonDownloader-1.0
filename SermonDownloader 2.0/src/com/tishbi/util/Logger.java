package com.tishbi.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger 
{
	private static enum LogType { ACTIVITY, EXCEPTION };
	
	public static void Log(String logText)
	{
		FileWriter activityLog;
		try 
		{
			activityLog = GetFile(LogType.ACTIVITY);
			activityLog.write(Utilities.logTimeStamp()+logText+"\n");
			System.out.println(logText);
			activityLog.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void Log(String logText,Exception obj)
	{
		try
		{
			FileWriter exceptionLog = GetFile(LogType.EXCEPTION);
			exceptionLog.write(Utilities.FullTimeStamp()+logText+"\n");
			BufferedWriter bw = new BufferedWriter(exceptionLog);
			PrintWriter out = new PrintWriter(bw);
			obj.printStackTrace(out);
			exceptionLog.write("\n");
			exceptionLog.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static FileWriter GetFile(LogType _logType) throws IOException
	{
		FileWriter fileWriter = null;
		String filename = null;
		String basePath = Globals.cachePath();
		File baseDir = new File(basePath);
		if (!baseDir.exists())
			baseDir.mkdirs();
		
		switch(_logType)
		{
			case ACTIVITY:
			{
				filename = basePath+Utilities.TimeStamp()+"_ACTIVITY.log"; break;
			}
			case EXCEPTION:
			{
				filename = basePath+Utilities.TimeStamp()+"_ERROR.log"; break;
			}
		}
		
		File file = new File(filename);
		if (!file.exists())
			file.createNewFile();
		
		fileWriter = new FileWriter(file,true);
		
		return fileWriter;
	}
	
	private static boolean exceedsSize(File file)
	{
		double size = (file.length())/1048;
		size = size/1048;
		return size > 5 ? true: false;
	}
}
