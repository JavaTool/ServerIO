package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountLoginRequest extends Request {

	private CS_AccountLogin cS_AccountLogin;

	public AccountLoginRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_AccountLogin cS_AccountLogin) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_AccountLogin = cS_AccountLogin;

	}
	public AccountLoginRequest(Request request, CS_AccountLogin cS_AccountLogin) {
		super(request);
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
