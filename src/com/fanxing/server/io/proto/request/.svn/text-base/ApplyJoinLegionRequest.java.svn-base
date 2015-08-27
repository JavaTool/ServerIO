package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ApplyJoinLegionRequest extends Request {

	private CS_ApplyJoinLegion cS_ApplyJoinLegion;

	public ApplyJoinLegionRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_ApplyJoinLegion cS_ApplyJoinLegion) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_ApplyJoinLegion = cS_ApplyJoinLegion;

	}
	public ApplyJoinLegionRequest(Request request, CS_ApplyJoinLegion cS_ApplyJoinLegion) {
		super(request);
		this.cS_ApplyJoinLegion = cS_ApplyJoinLegion;
	}

	public ApplyJoinLegionRequest(IContent content, CS_ApplyJoinLegion cS_ApplyJoinLegion) {
		super(content);
		this.cS_ApplyJoinLegion = cS_ApplyJoinLegion;
	}

	/**
	 * 
	 * @return	军团
	 */
	public Integer getLegionId() {
		return cS_ApplyJoinLegion.getLegionId();
	}

	public CS_ApplyJoinLegion getCS_ApplyJoinLegion() {
		return cS_ApplyJoinLegion;
	}

	@Override
	public byte[] getByteArray() {
		return cS_ApplyJoinLegion.toByteArray();
	}

}
