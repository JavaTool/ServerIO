package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.http.HttpSession;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountRegisterRequest extends Request {

	private CS_AccountRegister cS_AccountRegister;

	public AccountRegisterRequest(String ip, String receiveMessageId, HttpSession session, CS_AccountRegister cS_AccountRegister) {
		super(ip, receiveMessageId, session);
		this.cS_AccountRegister = cS_AccountRegister;

	}
	public AccountRegisterRequest(Request request, CS_AccountRegister cS_AccountRegister) {
		super(request);
		this.cS_AccountRegister = cS_AccountRegister;
	}

	/**
	 * 
	 * @return	邮箱
	 */
	public String getEmail() {
		return cS_AccountRegister.getEmail();
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
	 * @return	密码
	 */
	public String getPassword() {
		return cS_AccountRegister.getPassword();
	}

	public CS_AccountRegister getCS_AccountRegister() {
		return cS_AccountRegister;
	}

	@Override
	public byte[] getByteArray() {
		return cS_AccountRegister.toByteArray();
	}

}
