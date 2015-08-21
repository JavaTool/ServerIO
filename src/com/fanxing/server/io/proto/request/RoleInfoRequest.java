package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class RoleInfoRequest extends Request {

	private CS_RoleInfo cS_RoleInfo;

	public RoleInfoRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_RoleInfo cS_RoleInfo) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_RoleInfo = cS_RoleInfo;

	}
	public RoleInfoRequest(Request request, CS_RoleInfo cS_RoleInfo) {
		super(request);
		this.cS_RoleInfo = cS_RoleInfo;
	}

	/**
	 * 
	 * @return	角色id
	 */
	public Integer getPlayerId() {
		return cS_RoleInfo.getPlayerId();
	}

	public CS_RoleInfo getCS_RoleInfo() {
		return cS_RoleInfo;
	}

	@Override
	public byte[] getByteArray() {
		return cS_RoleInfo.toByteArray();
	}

}
