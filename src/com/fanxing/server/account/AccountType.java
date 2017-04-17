package com.fanxing.server.account;

public enum AccountType {
	
	/**
	* <code>ACCOUNT = 1;</code>
	*
	* <pre>
	* 普通帐号
	* </pre>
	*/
	ACCOUNT(0, 1),
	/**
	* <code>TENCENT = 2;</code>
	*
	* <pre>
	* 腾讯
	* </pre>
	*/
	TENCENT(1, 2),
	;
	
	/**
	* <code>ACCOUNT = 1;</code>
	*
	* <pre>
	* 普通帐号
	* </pre>
	*/
	public static final int ACCOUNT_VALUE = 1;
	/**
	* <code>TENCENT = 2;</code>
	*
	* <pre>
	* 腾讯
	* </pre>
	*/
	public static final int TENCENT_VALUE = 2;
	
	public final int getNumber() { return value; }
	
	public static AccountType valueOf(int value) {
		switch (value) {
		case 1: return ACCOUNT;
		case 2: return TENCENT;
		default: return null;
		}
	}
    
    private final int value;

    private AccountType(int index, int value) {
    	this.value = value;
    }
	
}
