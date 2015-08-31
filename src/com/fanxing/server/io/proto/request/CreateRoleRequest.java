package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class CreateRoleRequest extends Request {

	private CS_CreateRole cS_CreateRole;

	public CreateRoleRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_CreateRole cS_CreateRole) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_CreateRole = cS_CreateRole;

	}
	public CreateRoleRequest(Request request, CS_CreateRole cS_CreateRole) {
		super(request);
		this.cS_CreateRole = cS_CreateRole;
	}

	public CreateRoleRequest(IContent content, CS_CreateRole cS_CreateRole) {
		super(content);
		this.cS_CreateRole = cS_CreateRole;
	}

	/**
	 * 
	 * @return	帐号
	 */
	public String getAccount() {
		return cS_CreateRole.getAccount();
	}

	public CS_CreateRole getCS_CreateRole() {
		return cS_CreateRole;
	}

	@Override
	public byte[] getByteArray() {
		return cS_CreateRole.toByteArray();
	}

}
