package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class DisconnectRequest extends Request {

	private CS_Disconnect cS_Disconnect;

	public DisconnectRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_Disconnect cS_Disconnect) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_Disconnect = cS_Disconnect;

	}
	public DisconnectRequest(Request request, CS_Disconnect cS_Disconnect) {
		super(request);
		this.cS_Disconnect = cS_Disconnect;
	}

	public DisconnectRequest(IContent content, CS_Disconnect cS_Disconnect) {
		super(content);
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
