package org.tool.server.io.configuration;

import org.tool.server.io.IConfigurationHolder;

public interface IConfigurationManager extends IConfigurationHolder{
	
	String PARAM_SERVERID = "serverId";
	
	String PARAM_CONFIG = "config";
	
	String PARAM_ATTRNAME = "attrName";
	
	//String getConfigurationValue(String configFileName, String key);
	
	//String getConfigurationValue(String configFileName, String key, String defaultValue);
}
