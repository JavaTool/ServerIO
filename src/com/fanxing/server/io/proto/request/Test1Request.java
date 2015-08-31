package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.TestProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class Test1Request extends Request {

	private CS_Test1 cS_Test1;

	public Test1Request(String ip, String receiveMessageId, String sessionId, ISender sender, CS_Test1 cS_Test1) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_Test1 = cS_Test1;

	}
	public Test1Request(Request request, CS_Test1 cS_Test1) {
		super(request);
		this.cS_Test1 = cS_Test1;
	}

	public Test1Request(IContent content, CS_Test1 cS_Test1) {
		super(content);
		this.cS_Test1 = cS_Test1;
	}

	/**
	 * 
	 * @return	<u>do not has any annotate.</u>
	 */
	public Integer getA() {
		return cS_Test1.getA();
	}

	public CS_Test1 getCS_Test1() {
		return cS_Test1;
	}

	@Override
	public byte[] getByteArray() {
		return cS_Test1.toByteArray();
	}

}
