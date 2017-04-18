package org.tool.server.account;

import java.io.Serializable;

public class TencentAccount implements IAuthenticateAccount, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String openId;
	
	private int userId;
	
	private int lastServerId;
	
	private int type;
	/**登录密钥*/
	private String loginKey;
	
	private String accessToken;
	
	private String payToken;
	
	private int flag;
	
	private String msg;
	
	private String pf;
	
	private String pf_key;
	
	private String ip;
	
	private String appId;

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int getLastServerId() {
		return lastServerId;
	}

	@Override
	public void setLastServerId(int lastServerId) {
		this.lastServerId = lastServerId;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int getAccountType() {
		return AccountType.TENCENT_VALUE;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getPayToken() {
		return payToken;
	}

	public void setPayToken(String payToken) {
		this.payToken = payToken;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getPf_key() {
		return pf_key;
	}

	public void setPf_key(String pf_key) {
		this.pf_key = pf_key;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("accessToken : ").append(accessToken).append(" ; ");
		builder.append("openId : ").append(openId).append(" ; ");
		builder.append("type : ").append(type).append(" ; ");
		builder.append("payToken : ").append(payToken).append(" ; ");
		builder.append("ip : ").append(ip).append(" ; ");
		builder.append("pf_key : ").append(pf_key).append(" ; ");
		builder.append("pf : ").append(pf).append(" ; ");
		return builder.toString();
	}

}
