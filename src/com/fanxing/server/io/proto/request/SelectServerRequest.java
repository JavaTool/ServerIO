package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class SelectServerRequest extends Request {

	private CS_SelectServer cS_SelectServer;

	public SelectServerRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_SelectServer cS_SelectServer) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_SelectServer = cS_SelectServer;

	}
	public SelectServerRequest(Request request, CS_SelectServer cS_SelectServer) {
		super(request);
		this.cS_SelectServer = cS_SelectServer;
	}

	public SelectServerRequest(IContent content, CS_SelectServer cS_SelectServer) {
		super(content);
		this.cS_SelectServer = cS_SelectServer;
	}

	/**
	 * 
	 * @return	<u>do not has any annotate.</u>
	 */
	public Integer getServerId() {
		return cS_SelectServer.getServerId();
	}

	public CS_SelectServer getCS_SelectServer() {
		return cS_SelectServer;
	}

	@Override
	public byte[] getByteArray() {
		return cS_SelectServer.toByteArray();
	}

}
