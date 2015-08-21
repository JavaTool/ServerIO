package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountGetRoleListRequest extends Request {

	private CS_AccountGetRoleList cS_AccountGetRoleList;

	public AccountGetRoleListRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_AccountGetRoleList cS_AccountGetRoleList) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_AccountGetRoleList = cS_AccountGetRoleList;

	}
	public AccountGetRoleListRequest(Request request, CS_AccountGetRoleList cS_AccountGetRoleList) {
		super(request);
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
