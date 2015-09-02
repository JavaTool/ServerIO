package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class RemoveMemberRequest extends Request {

	private CS_RemoveMember cS_RemoveMember;

	public RemoveMemberRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_RemoveMember cS_RemoveMember) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_RemoveMember = cS_RemoveMember;

	}
	public RemoveMemberRequest(Request request, CS_RemoveMember cS_RemoveMember) {
		super(request);
		this.cS_RemoveMember = cS_RemoveMember;
	}

	public RemoveMemberRequest(IContent content, CS_RemoveMember cS_RemoveMember) {
		super(content);
		this.cS_RemoveMember = cS_RemoveMember;
	}

	/**
	 * 
	 * @return	角色id
	 */
	public Integer getPlayerId() {
		return cS_RemoveMember.getPlayerId();
	}

	public CS_RemoveMember getCS_RemoveMember() {
		return cS_RemoveMember;
	}

	@Override
	public byte[] getByteArray() {
		return cS_RemoveMember.toByteArray();
	}

}
