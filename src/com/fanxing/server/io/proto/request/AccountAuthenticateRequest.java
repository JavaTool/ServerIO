package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountAuthenticateRequest extends Request {

	private CS_AccountAuthenticate cS_AccountAuthenticate;

	public AccountAuthenticateRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_AccountAuthenticate cS_AccountAuthenticate) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_AccountAuthenticate = cS_AccountAuthenticate;

	}
	public AccountAuthenticateRequest(Request request, CS_AccountAuthenticate cS_AccountAuthenticate) {
		super(request);
		this.cS_AccountAuthenticate = cS_AccountAuthenticate;
	}

	public AccountAuthenticateRequest(IContent content, CS_AccountAuthenticate cS_AccountAuthenticate) {
		super(content);
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
