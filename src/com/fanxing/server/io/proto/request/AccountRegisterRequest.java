package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountRegisterRequest extends Request {

	private CS_AccountRegister cS_AccountRegister;

	public AccountRegisterRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_AccountRegister cS_AccountRegister) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_AccountRegister = cS_AccountRegister;

	}
	public AccountRegisterRequest(Request request, CS_AccountRegister cS_AccountRegister) {
		super(request);
		this.cS_AccountRegister = cS_AccountRegister;
	}

	/**
	 * 
	 * @return	密码
	 */
	public String getPassword() {
		return cS_AccountRegister.getPassword();
	}

	/**
	 * 
	 * @return	帐号
	 */
	public String getAccount() {
		return cS_AccountRegister.getAccount();
	}

	/**
	 * 
	 * @return	邮箱
	 */
	public String getEmail() {
		return cS_AccountRegister.getEmail();
	}

	public CS_AccountRegister getCS_AccountRegister() {
		return cS_AccountRegister;
	}

	@Override
	public byte[] getByteArray() {
		return cS_AccountRegister.toByteArray();
	}

}
