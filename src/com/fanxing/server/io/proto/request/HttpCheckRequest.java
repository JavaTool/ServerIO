package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class HttpCheckRequest extends Request {

	private CS_HttpCheck cS_HttpCheck;

	public HttpCheckRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_HttpCheck cS_HttpCheck) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_HttpCheck = cS_HttpCheck;

	}
	public HttpCheckRequest(Request request, CS_HttpCheck cS_HttpCheck) {
		super(request);
		this.cS_HttpCheck = cS_HttpCheck;
	}

	public HttpCheckRequest(IContent content, CS_HttpCheck cS_HttpCheck) {
		super(content);
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
