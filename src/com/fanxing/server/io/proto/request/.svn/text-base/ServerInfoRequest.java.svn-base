package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ServerInfoRequest extends Request {

	private VO_ServerInfo vO_ServerInfo;

	public ServerInfoRequest(String ip, String receiveMessageId, String sessionId, ISender sender, VO_ServerInfo vO_ServerInfo) {
		super(ip, receiveMessageId, sessionId, sender);
		this.vO_ServerInfo = vO_ServerInfo;

	}
	public ServerInfoRequest(Request request, VO_ServerInfo vO_ServerInfo) {
		super(request);
		this.vO_ServerInfo = vO_ServerInfo;
	}

	public ServerInfoRequest(IContent content, VO_ServerInfo vO_ServerInfo) {
		super(content);
		this.vO_ServerInfo = vO_ServerInfo;
	}

	/**
	 * 
	 * @return	服务器名称
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
	 * @return	服务器地址
	 */
	public String getUrl() {
		return vO_ServerInfo.getUrl();
	}

	/**
	 * 
	 * @return	服务器状态
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
