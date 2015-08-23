package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountAuthenticateRequest extends Request {

	private CS_AccountAuthenticate cS_AccountAuthenticate;

	public AccountAuthenticateRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_AccountAuthenticate cS_AccountAuthenticate) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_AccountAuthenticate = cS_AccountAuthenticate;

	}
	public AccountAuthenticateRequest(Request request, CS_AccountAuthenticate cS_AccountAuthenticate) {
		super(request);
		this.cS_AccountAuthenticate = cS_AccountAuthenticate;
	}

	/**
	 * 
	 * @return	帐号id
	 */
	public Integer getAccountId() {
		return cS_AccountAuthenticate.getAccountId();
	}

	/**
	 * 
	 * @return	帐号
	 */
	public String getAccount() {
		return cS_AccountAuthenticate.getAccount();
	}

	/**
	 * 
	 * @return	认证密钥
	 */
	public String getKey() {
		return cS_AccountAuthenticate.getKey();
	}

	public CS_AccountAuthenticate getCS_AccountAuthenticate() {
		return cS_AccountAuthenticate;
	}

	@Override
	public byte[] getByteArray() {
		return cS_AccountAuthenticate.toByteArray();
	}

}
