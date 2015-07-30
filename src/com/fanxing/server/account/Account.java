package com.fanxing.server.account;

import java.io.Serializable;

import com.fanxing.server.io.mina.Identity;

/**
 * 帐号信息
 * @author 	fuhuiyuan
 */
public class Account implements Identity, Serializable {
	
	private static final long serialVersionUID = -2106017203062441807L;
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

	@Override
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

}
