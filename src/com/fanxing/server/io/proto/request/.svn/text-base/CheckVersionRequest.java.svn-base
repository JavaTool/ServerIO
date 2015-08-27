package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;
import java.util.List;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class CheckVersionRequest extends Request {

	private CS_CheckVersion cS_CheckVersion;

	public CheckVersionRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_CheckVersion cS_CheckVersion) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_CheckVersion = cS_CheckVersion;

	}
	public CheckVersionRequest(Request request, CS_CheckVersion cS_CheckVersion) {
		super(request);
		this.cS_CheckVersion = cS_CheckVersion;
	}

	public CheckVersionRequest(IContent content, CS_CheckVersion cS_CheckVersion) {
		super(content);
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
	 * @return	客户端版本
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
