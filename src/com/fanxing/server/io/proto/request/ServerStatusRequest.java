package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.http.HttpSession;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ServerStatusRequest extends Request {

	private CS_ServerStatus cS_ServerStatus;

	public ServerStatusRequest(String ip, String receiveMessageId, HttpSession session, CS_ServerStatus cS_ServerStatus) {
		super(ip, receiveMessageId, session);
		this.cS_ServerStatus = cS_ServerStatus;

	}
	public ServerStatusRequest(Request request, CS_ServerStatus cS_ServerStatus) {
		super(request);
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
