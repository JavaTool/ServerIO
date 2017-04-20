package org.tool.server.account;

import java.io.Serializable;

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
	/**元宝*/
	private int imoney;
	/**帐号id*/
	private int id;
	/**帐号邮箱*/
	private String email;
	/**登录密钥*/
	private String loginKey;
	/**用户ip*/
	private String ip;
	
	private int lastServerId;

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

	public int getImoney() {
		return imoney;
	}

	public void setImoney(int imoney) {
		this.imoney = imoney;
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
		return "id[" + getId() + "] name[" + getName() + "]";
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
		return "";
	}

	@Override
	public String getOpenId() {
		return "";
	}

}