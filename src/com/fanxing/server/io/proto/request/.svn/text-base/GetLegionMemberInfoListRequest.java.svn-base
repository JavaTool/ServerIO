package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetLegionMemberInfoListRequest extends Request {

	private CS_GetLegionMemberInfoList cS_GetLegionMemberInfoList;

	public GetLegionMemberInfoListRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_GetLegionMemberInfoList cS_GetLegionMemberInfoList) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_GetLegionMemberInfoList = cS_GetLegionMemberInfoList;

	}
	public GetLegionMemberInfoListRequest(Request request, CS_GetLegionMemberInfoList cS_GetLegionMemberInfoList) {
		super(request);
		this.cS_GetLegionMemberInfoList = cS_GetLegionMemberInfoList;
	}

	public GetLegionMemberInfoListRequest(IContent content, CS_GetLegionMemberInfoList cS_GetLegionMemberInfoList) {
		super(content);
		this.cS_GetLegionMemberInfoList = cS_GetLegionMemberInfoList;
	}

	/**
	 * 
	 * @return	军团id
	 */
	public Integer getLegionId() {
		return cS_GetLegionMemberInfoList.getLegionId();
	}

	public CS_GetLegionMemberInfoList getCS_GetLegionMemberInfoList() {
		return cS_GetLegionMemberInfoList;
	}

	@Override
	public byte[] getByteArray() {
		return cS_GetLegionMemberInfoList.toByteArray();
	}

}
