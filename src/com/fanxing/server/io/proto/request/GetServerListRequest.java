package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetServerListRequest extends Request {

	private CS_GetServerList cS_GetServerList;

	public GetServerListRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_GetServerList cS_GetServerList) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_GetServerList = cS_GetServerList;

	}
	public GetServerListRequest(Request request, CS_GetServerList cS_GetServerList) {
		super(request);
		this.cS_GetServerList = cS_GetServerList;
	}

	public GetServerListRequest(IContent content, CS_GetServerList cS_GetServerList) {
		super(content);
		this.cS_GetServerList = cS_GetServerList;
	}

	/**
	 * 
	 * @return	帐号id
	 */
	public Integer getAccountId() {
		return cS_GetServerList.getAccountId();
	}

	public CS_GetServerList getCS_GetServerList() {
		return cS_GetServerList;
	}

	@Override
	public byte[] getByteArray() {
		return cS_GetServerList.toByteArray();
	}

}
