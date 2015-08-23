package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class HttpCheckRequest extends Request {

	private CS_HttpCheck cS_HttpCheck;

	public HttpCheckRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_HttpCheck cS_HttpCheck) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_HttpCheck = cS_HttpCheck;

	}
	public HttpCheckRequest(Request request, CS_HttpCheck cS_HttpCheck) {
		super(request);
		this.cS_HttpCheck = cS_HttpCheck;
	}

	/**
	 * 
	 * @return	<u>do not has any annotate.</u>
	 */
	public String getMessageId() {
		return cS_HttpCheck.getMessageId();
	}

	public CS_HttpCheck getCS_HttpCheck() {
		return cS_HttpCheck;
	}

	@Override
	public byte[] getByteArray() {
		return cS_HttpCheck.toByteArray();
	}

}
