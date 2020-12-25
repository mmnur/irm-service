package com.example.irm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	
	private LogUtil()
	{		
		// Do not allow using default constructor
	}
	
	public static Logger getLogger()
	{
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();		 
        String callersClassName = stackTrace[2].getClassName();
        return LoggerFactory.getLogger(callersClassName);
	}
}
