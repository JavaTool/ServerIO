package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ServerInfoRequest extends Request {

	private VO_ServerInfo vO_ServerInfo;

	public ServerInfoRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, VO_ServerInfo vO_ServerInfo) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.vO_ServerInfo = vO_ServerInfo;

	}
	public ServerInfoRequest(Request request, VO_ServerInfo vO_ServerInfo) {
		super(request);
		this.vO_ServerInfo = vO_ServerInfo;
	}

	/**
	 * 
	 * @return	服务器名�?
	 */
	public String getName() {
		return vO_ServerInfo.getName();
	}

	/**
	 * 
	 * @return	服务器id
	 */
	public Integer getId() {
		return vO_ServerInfo.getId();
	}

	/**
	 * 
	 * @return	服务器地�?
	 */
	public String getUrl() {
		return vO_ServerInfo.getUrl();
	}

	/**
	 * 
	 * @return	服务器状�?
	 */
	public String getStatus() {
		return vO_ServerInfo.getStatus();
	}

	public VO_ServerInfo getVO_ServerInfo() {
		return vO_ServerInfo;
	}

	@Override
	public byte[] getByteArray() {
		return vO_ServerInfo.toByteArray();
	}

}
