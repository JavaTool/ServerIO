package org.tool.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	
	protected LogUtil() {}
	
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}
	
	public static Logger getLogger(String name) {
		return LoggerFactory.getLogger(name);
	}

}
