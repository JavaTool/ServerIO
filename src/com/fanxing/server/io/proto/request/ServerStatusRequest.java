package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ServerStatusRequest extends Request {

	private CS_ServerStatus cS_ServerStatus;

	public ServerStatusRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_ServerStatus cS_ServerStatus) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_ServerStatus = cS_ServerStatus;

	}
	public ServerStatusRequest(Request request, CS_ServerStatus cS_ServerStatus) {
		super(request);
		this.cS_ServerStatus = cS_ServerStatus;
	}

	public ServerStatusRequest(IContent content, CS_ServerStatus cS_ServerStatus) {
		super(content);
		this.cS_ServerStatus = cS_ServerStatus;
	}

	/**
	 * 
	 * @return	服务器状�?
	 */
	public String getServerStatus() {
		return cS_ServerStatus.getServerStatus();
	}

	public CS_ServerStatus getCS_ServerStatus() {
		return cS_ServerStatus;
	}

	@Override
	public byte[] getByteArray() {
		return cS_ServerStatus.toByteArray();
	}

}
