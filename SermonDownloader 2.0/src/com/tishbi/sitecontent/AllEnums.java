package com.tishbi.sitecontent;

public class AllEnums 
{
	public enum DownloadStatus
	{
		NOT_STARTED,
		DOWNLOADING,
		FAILED,
		COMPLETED,
		ALREADY_DONE,
		PLAYING
	};
	
	public enum OS 
	{
		WINDOWS,
		UNIX_OR_LINUX,
		MAC,
		SOLARIS,
		UNKNOWN
	};
	
	public enum UpdateStatus
	{
		UPDATE_NOT_FOUND,
		UPDATE_FAILED,
		UPDATE_SUCCESSFUL,
		UNKNOWN
	};
}
