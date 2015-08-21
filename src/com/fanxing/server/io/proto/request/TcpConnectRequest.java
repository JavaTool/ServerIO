package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.TcpProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TcpConnectRequest extends Request {

	private CS_TcpConnect cS_TcpConnect;

	public TcpConnectRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_TcpConnect cS_TcpConnect) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_TcpConnect = cS_TcpConnect;

	}
	public TcpConnectRequest(Request request, CS_TcpConnect cS_TcpConnect) {
		super(request);
		this.cS_TcpConnect = cS_TcpConnect;
	}

	/**
	 * 
	 * @return	<u>do not has any annotate.</u>
	 */
	public String getKey() {
		return cS_TcpConnect.getKey();
	}

	public CS_TcpConnect getCS_TcpConnect() {
		return cS_TcpConnect;
	}

	@Override
	public byte[] getByteArray() {
		return cS_TcpConnect.toByteArray();
	}

}
