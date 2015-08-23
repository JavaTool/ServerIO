package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.TestProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class Test1Request extends Request {

	private CS_Test1 cS_Test1;

	public Test1Request(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_Test1 cS_Test1) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_Test1 = cS_Test1;

	}
	public Test1Request(Request request, CS_Test1 cS_Test1) {
		super(request);
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
