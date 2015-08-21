package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;
import java.util.List;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class CheckVersionRequest extends Request {

	private CS_CheckVersion cS_CheckVersion;

	public CheckVersionRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_CheckVersion cS_CheckVersion) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_CheckVersion = cS_CheckVersion;

	}
	public CheckVersionRequest(Request request, CS_CheckVersion cS_CheckVersion) {
		super(request);
		this.cS_CheckVersion = cS_CheckVersion;
	}

	/**
	 * 
	 * @return	文件版本列表
	 */
	public List<VO_FileVersion> getDbVersionsList() {
		return cS_CheckVersion.getDbVersionsList();
	}

	/**
	 * 
	 * @return	客户端版�?
	 */
	public String getClientVersion() {
		return cS_CheckVersion.getClientVersion();
	}

	public CS_CheckVersion getCS_CheckVersion() {
		return cS_CheckVersion;
	}

	@Override
	public byte[] getByteArray() {
		return cS_CheckVersion.toByteArray();
	}

}
