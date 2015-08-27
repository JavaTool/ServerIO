package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetLegionInfoRequest extends Request {

	private CS_GetLegionInfo cS_GetLegionInfo;

	public GetLegionInfoRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_GetLegionInfo cS_GetLegionInfo) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_GetLegionInfo = cS_GetLegionInfo;

	}
	public GetLegionInfoRequest(Request request, CS_GetLegionInfo cS_GetLegionInfo) {
		super(request);
		this.cS_GetLegionInfo = cS_GetLegionInfo;
	}

	public GetLegionInfoRequest(IContent content, CS_GetLegionInfo cS_GetLegionInfo) {
		super(content);
		this.cS_GetLegionInfo = cS_GetLegionInfo;
	}

	/**
	 * 
	 * @return	军团id
	 */
	public Integer getLegionId() {
		return cS_GetLegionInfo.getLegionId();
	}

	public CS_GetLegionInfo getCS_GetLegionInfo() {
		return cS_GetLegionInfo;
	}

	@Override
	public byte[] getByteArray() {
		return cS_GetLegionInfo.toByteArray();
	}

}
