package com.fanxing.server.account;

import java.io.Serializable;

public interface IAuthenticateAccount extends Serializable {
	
	int getId();
	
	int getLastServerId();
	
	void setLastServerId(int lastServerId);
	
	int getAccountType();
	
	String getLoginKey();
	
	void setLoginKey(String key);
	
	String getAppId();
	
	String getOpenId();
	
}
