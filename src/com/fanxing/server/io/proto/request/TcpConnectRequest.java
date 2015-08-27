package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.TcpProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TcpConnectRequest extends Request {

	private CS_TcpConnect cS_TcpConnect;

	public TcpConnectRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_TcpConnect cS_TcpConnect) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_TcpConnect = cS_TcpConnect;

	}
	public TcpConnectRequest(Request request, CS_TcpConnect cS_TcpConnect) {
		super(request);
		this.cS_TcpConnect = cS_TcpConnect;
	}

	public TcpConnectRequest(IContent content, CS_TcpConnect cS_TcpConnect) {
		super(content);
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
