package com.fanxing.server.io.proto;

import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;
import com.fanxing.server.io.dispatch.IContentHandler;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.RoleProtos.SC_RoleInfo;
import com.fanxing.server.io.proto.request.SC_RoleInfoRequest;
import com.fanxing.server.io.proto.returner.RoleReturner;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ReturnHandler implements IContentHandler {

	private RoleReturner roleReturner;

	@Override
	public void handle(IContent content) throws Exception {
		switch (MessageId.valueOf(content.getMessageId())) {
		case MISC_RoleInfoResp : 
			if (getRoleReturner() != null) {
				SC_RoleInfo sC_RoleInfo = SC_RoleInfo.parseFrom(content.getDatas());
				SC_RoleInfoRequest sC_RoleInfoRequest = new SC_RoleInfoRequest(content, sC_RoleInfo);
				getRoleReturner().processSC_RoleInfo(sC_RoleInfoRequest);
			}
			break;
		default : 
		}
	}

	private RoleReturner getRoleReturner() {
		return roleReturner;
	}

	public void setRoleReturner(RoleReturner roleReturner) {
		this.roleReturner = roleReturner;
	}

}
