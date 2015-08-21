package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class DisconnectRequest extends Request {

	private CS_Disconnect cS_Disconnect;

	public DisconnectRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_Disconnect cS_Disconnect) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_Disconnect = cS_Disconnect;

	}
	public DisconnectRequest(Request request, CS_Disconnect cS_Disconnect) {
		super(request);
		this.cS_Disconnect = cS_Disconnect;
	}

	/**
	 * 
	 * @return	帐号id
	 */
	public Integer getAccountId() {
		return cS_Disconnect.getAccountId();
	}

	public CS_Disconnect getCS_Disconnect() {
		return cS_Disconnect;
	}

	@Override
	public byte[] getByteArray() {
		return cS_Disconnect.toByteArray();
	}

}
