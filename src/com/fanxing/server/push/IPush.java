package com.fanxing.server.push;

import com.alibaba.fastjson.JSONObject;
import com.fanxing.server.io.IConfigurationHolder;

public interface IPush {
	
	String PARAM_APP = "app";
	
	String PARAM_TIME = "time";
	
	String PARAM_MSG = "msg";
	
	String PARAM_ALIAS = "alias";
	
	String PARAM_TAG = "tag";
	
	String PARAM_ID = "id";
	
	String PARAM_SPLIT = ";";
	
	JSONObject pushAll(String msg);
	
	JSONObject pushAlias(String msg, String... aliases);
	
	JSONObject pushTag(String msg, String... tags);
	
	JSONObject pushId(String msg, String... ids);
	
	JSONObject schedulePushAll(String time, String msg);
	
	JSONObject schedulePushAlias(String time, String msg, String... aliases);
	
	JSONObject schedulePushTag(String time, String msg, String... tags);
	
	JSONObject schedulePushId(String time, String msg, String... ids);
	
	String[] getApps();
	
	void load(IConfigurationHolder configurationHolder);

}
