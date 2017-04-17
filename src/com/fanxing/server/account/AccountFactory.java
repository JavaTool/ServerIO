package com.fanxing.server.account;

import com.alibaba.fastjson.JSONObject;

public class AccountFactory implements IAccountFactory {

	@Override
	public IAuthenticateAccount createAccount(String value) {
		return JSONObject.parseObject(value, Account.class);
	}

}
