package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class InviteJoinLegionRequest extends Request {

	private CS_InviteJoinLegion cS_InviteJoinLegion;

	public InviteJoinLegionRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_InviteJoinLegion cS_InviteJoinLegion) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_InviteJoinLegion = cS_InviteJoinLegion;

	}
	public InviteJoinLegionRequest(Request request, CS_InviteJoinLegion cS_InviteJoinLegion) {
		super(request);
		this.cS_InviteJoinLegion = cS_InviteJoinLegion;
	}

	public InviteJoinLegionRequest(IContent content, CS_InviteJoinLegion cS_InviteJoinLegion) {
		super(content);
		this.cS_InviteJoinLegion = cS_InviteJoinLegion;
	}

	/**
	 * 
	 * @return	角色id
	 */
	public Integer getTargetId() {
		return cS_InviteJoinLegion.getTargetId();
	}

	public CS_InviteJoinLegion getCS_InviteJoinLegion() {
		return cS_InviteJoinLegion;
	}

	@Override
	public byte[] getByteArray() {
		return cS_InviteJoinLegion.toByteArray();
	}

}
