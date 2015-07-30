package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.http.HttpSession;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountGetRoleListRequest extends Request {

	private CS_AccountGetRoleList cS_AccountGetRoleList;

	public AccountGetRoleListRequest(String ip, String receiveMessageId, HttpSession session, CS_AccountGetRoleList cS_AccountGetRoleList) {
		super(ip, receiveMessageId, session);
		this.cS_AccountGetRoleList = cS_AccountGetRoleList;

	}
	public AccountGetRoleListRequest(Request request, CS_AccountGetRoleList cS_AccountGetRoleList) {
		super(request);
		this.cS_AccountGetRoleList = cS_AccountGetRoleList;
	}

	/**
	 * 
	 * @return	帐号id
	 */
	public Integer getAccountId() {
		return cS_AccountGetRoleList.getAccountId();
	}

	/**
	 * 
	 * @return	帐号
	 */
	public String getAccount() {
		return cS_AccountGetRoleList.getAccount();
	}

	public CS_AccountGetRoleList getCS_AccountGetRoleList() {
		return cS_AccountGetRoleList;
	}

	@Override
	public byte[] getByteArray() {
		return cS_AccountGetRoleList.toByteArray();
	}

}
