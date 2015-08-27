package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class PermissionManageRequest extends Request {

	private CS_PermissionManage cS_PermissionManage;

	public PermissionManageRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_PermissionManage cS_PermissionManage) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_PermissionManage = cS_PermissionManage;

	}
	public PermissionManageRequest(Request request, CS_PermissionManage cS_PermissionManage) {
		super(request);
		this.cS_PermissionManage = cS_PermissionManage;
	}

	public PermissionManageRequest(IContent content, CS_PermissionManage cS_PermissionManage) {
		super(content);
		this.cS_PermissionManage = cS_PermissionManage;
	}

	/**
	 * 
	 * @return	职位类型
	 */
	public Integer getTitle() {
		return cS_PermissionManage.getTitle();
	}

	/**
	 * 
	 * @return	角色id
	 */
	public Integer getPlayerId() {
		return cS_PermissionManage.getPlayerId();
	}

	public CS_PermissionManage getCS_PermissionManage() {
		return cS_PermissionManage;
	}

	@Override
	public byte[] getByteArray() {
		return cS_PermissionManage.toByteArray();
	}

}
