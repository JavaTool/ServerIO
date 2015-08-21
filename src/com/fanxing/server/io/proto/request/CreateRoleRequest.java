package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class CreateRoleRequest extends Request {

	private CS_CreateRole cS_CreateRole;

	public CreateRoleRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_CreateRole cS_CreateRole) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_CreateRole = cS_CreateRole;

	}
	public CreateRoleRequest(Request request, CS_CreateRole cS_CreateRole) {
		super(request);
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
