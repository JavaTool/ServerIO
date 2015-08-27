package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountLoginRequest extends Request {

	private CS_AccountLogin cS_AccountLogin;

	public AccountLoginRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_AccountLogin cS_AccountLogin) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_AccountLogin = cS_AccountLogin;

	}
	public AccountLoginRequest(Request request, CS_AccountLogin cS_AccountLogin) {
		super(request);
		this.cS_AccountLogin = cS_AccountLogin;
	}

	public AccountLoginRequest(IContent content, CS_AccountLogin cS_AccountLogin) {
		super(content);
		this.cS_AccountLogin = cS_AccountLogin;
	}

	/**
	 * 
	 * @return	密码
	 */
	public String getPassword() {
		return cS_AccountLogin.getPassword();
	}

	/**
	 * 
	 * @return	帐号
	 */
	public String getAccount() {
		return cS_AccountLogin.getAccount();
	}

	public CS_AccountLogin getCS_AccountLogin() {
		return cS_AccountLogin;
	}

	@Override
	public byte[] getByteArray() {
		return cS_AccountLogin.toByteArray();
	}

}
