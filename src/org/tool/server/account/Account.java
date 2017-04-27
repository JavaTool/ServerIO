package org.tool.server.account;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 帐号信息
 * @author 	fuhuiyuan
 */
public class Account implements IAuthenticateAccount, Serializable {
	
	private static final long serialVersionUID = 1L;
	/**帐号名称*/
	private String name;
	/**帐号密码*/
	private String password;
	/**货币*/
	private int currency;
	/**帐号id*/
	private int id;
	/**帐号邮箱*/
	private String email;
	/**登录密钥*/
	private String loginKey;
	/**用户ip*/
	private String ip;
	
	private int lastServerId;
	
	private String appId;
	
	private String appKey;
	
	private String openId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public int getLastServerId() {
		return lastServerId;
	}

	@Override
	public void setLastServerId(int lastServerId) {
		this.lastServerId = lastServerId;
	}

	@Override
	public int getAccountType() {
		return AccountType.ACCOUNT_VALUE;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	@Override
	public String getOpenId() {
		return openId;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
