package io.account;

import io.Identity;

public class Account implements Identity {
	
	public static final byte LOGIN_ERROR_NULL = 0;
	
	public static final byte LOGIN_ERROR_REPEAT = 1;
	
	private String name, password;
	
	private int imoney, id;

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

	public synchronized void setImoney(int imoney) {
		this.imoney = imoney;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "id[" + getId() + "] name[" + getName() + "]";
	}

}
