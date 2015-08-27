package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ReplyApplyJoinLegionRequest extends Request {

	private CS_ReplyApplyJoinLegion cS_ReplyApplyJoinLegion;

	public ReplyApplyJoinLegionRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_ReplyApplyJoinLegion cS_ReplyApplyJoinLegion) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_ReplyApplyJoinLegion = cS_ReplyApplyJoinLegion;

	}
	public ReplyApplyJoinLegionRequest(Request request, CS_ReplyApplyJoinLegion cS_ReplyApplyJoinLegion) {
		super(request);
		this.cS_ReplyApplyJoinLegion = cS_ReplyApplyJoinLegion;
	}

	public ReplyApplyJoinLegionRequest(IContent content, CS_ReplyApplyJoinLegion cS_ReplyApplyJoinLegion) {
		super(content);
		this.cS_ReplyApplyJoinLegion = cS_ReplyApplyJoinLegion;
	}

	/**
	 * 
	 * @return	角色id
	 */
	public Integer getTargetId() {
		return cS_ReplyApplyJoinLegion.getTargetId();
	}

	public CS_ReplyApplyJoinLegion getCS_ReplyApplyJoinLegion() {
		return cS_ReplyApplyJoinLegion;
	}

	@Override
	public byte[] getByteArray() {
		return cS_ReplyApplyJoinLegion.toByteArray();
	}

}
