package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ReplyInvitationRequest extends Request {

	private CS_ReplyInvitation cS_ReplyInvitation;

	public ReplyInvitationRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_ReplyInvitation cS_ReplyInvitation) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_ReplyInvitation = cS_ReplyInvitation;

	}
	public ReplyInvitationRequest(Request request, CS_ReplyInvitation cS_ReplyInvitation) {
		super(request);
		this.cS_ReplyInvitation = cS_ReplyInvitation;
	}

	public ReplyInvitationRequest(IContent content, CS_ReplyInvitation cS_ReplyInvitation) {
		super(content);
		this.cS_ReplyInvitation = cS_ReplyInvitation;
	}

	/**
	 * 
	 * @return	角色id
	 */
	public Integer getTargetId() {
		return cS_ReplyInvitation.getTargetId();
	}

	public CS_ReplyInvitation getCS_ReplyInvitation() {
		return cS_ReplyInvitation;
	}

	@Override
	public byte[] getByteArray() {
		return cS_ReplyInvitation.toByteArray();
	}

}
