package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class FileVersionRequest extends Request {

	private VO_FileVersion vO_FileVersion;

	public FileVersionRequest(String ip, String receiveMessageId, String sessionId, ISender sender, VO_FileVersion vO_FileVersion) {
		super(ip, receiveMessageId, sessionId, sender);
		this.vO_FileVersion = vO_FileVersion;

	}
	public FileVersionRequest(Request request, VO_FileVersion vO_FileVersion) {
		super(request);
		this.vO_FileVersion = vO_FileVersion;
	}

	public FileVersionRequest(IContent content, VO_FileVersion vO_FileVersion) {
		super(content);
		this.vO_FileVersion = vO_FileVersion;
	}

	/**
	 * 
	 * @return	文件名称
	 */
	public String getFileName() {
		return vO_FileVersion.getFileName();
	}

	/**
	 * 
	 * @return	版本号
	 */
	public String getVersion() {
		return vO_FileVersion.getVersion();
	}

	public VO_FileVersion getVO_FileVersion() {
		return vO_FileVersion;
	}

	@Override
	public byte[] getByteArray() {
		return vO_FileVersion.toByteArray();
	}

}
