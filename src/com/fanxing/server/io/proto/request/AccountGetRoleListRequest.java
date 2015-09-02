package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountGetRoleListRequest extends Request {

	private CS_AccountGetRoleList cS_AccountGetRoleList;

	public AccountGetRoleListRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_AccountGetRoleList cS_AccountGetRoleList) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_AccountGetRoleList = cS_AccountGetRoleList;

	}
	public AccountGetRoleListRequest(Request request, CS_AccountGetRoleList cS_AccountGetRoleList) {
		super(request);
		this.cS_AccountGetRoleList = cS_AccountGetRoleList;
	}

	public AccountGetRoleListRequest(IContent content, CS_AccountGetRoleList cS_AccountGetRoleList) {
		super(content);
		this.cS_AccountGetRoleList = cS_AccountGetRoleList;
	}

	/**
	 * 
	 * @return	<u>do not has any annotate.</u>
	 */
	public String getKey() {
		return cS_AccountGetRoleList.getKey();
	}

	public CS_AccountGetRoleList getCS_AccountGetRoleList() {
		return cS_AccountGetRoleList;
	}

	@Override
	public byte[] getByteArray() {
		return cS_AccountGetRoleList.toByteArray();
	}

}
