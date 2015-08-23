package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetServerListRequest extends Request {

	private CS_GetServerList cS_GetServerList;

	public GetServerListRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_GetServerList cS_GetServerList) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_GetServerList = cS_GetServerList;

	}
	public GetServerListRequest(Request request, CS_GetServerList cS_GetServerList) {
		super(request);
		this.cS_GetServerList = cS_GetServerList;
	}

	/**
	 * 
	 * @return	帐号id
	 */
	public Integer getAccountId() {
		return cS_GetServerList.getAccountId();
	}

	public CS_GetServerList getCS_GetServerList() {
		return cS_GetServerList;
	}

	@Override
	public byte[] getByteArray() {
		return cS_GetServerList.toByteArray();
	}

}
