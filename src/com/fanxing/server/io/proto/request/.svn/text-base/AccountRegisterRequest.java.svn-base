package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountRegisterRequest extends Request {

	private CS_AccountRegister cS_AccountRegister;

	public AccountRegisterRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_AccountRegister cS_AccountRegister) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_AccountRegister = cS_AccountRegister;

	}
	public AccountRegisterRequest(Request request, CS_AccountRegister cS_AccountRegister) {
		super(request);
		this.cS_AccountRegister = cS_AccountRegister;
	}

	public AccountRegisterRequest(IContent content, CS_AccountRegister cS_AccountRegister) {
		super(content);
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
