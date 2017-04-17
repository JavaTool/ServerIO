package com.fanxing.server.account;

public interface IAccountFactory {
	
	IAuthenticateAccount createAccount(String value);

}
