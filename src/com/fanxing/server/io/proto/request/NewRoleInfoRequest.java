package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class NewRoleInfoRequest extends Request {

	private CS_NewRoleInfo cS_NewRoleInfo;

	public NewRoleInfoRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_NewRoleInfo cS_NewRoleInfo) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_NewRoleInfo = cS_NewRoleInfo;

	}
	public NewRoleInfoRequest(Request request, CS_NewRoleInfo cS_NewRoleInfo) {
		super(request);
		this.cS_NewRoleInfo = cS_NewRoleInfo;
	}

	public NewRoleInfoRequest(IContent content, CS_NewRoleInfo cS_NewRoleInfo) {
		super(content);
		this.cS_NewRoleInfo = cS_NewRoleInfo;
	}

	/**
	 * 
	 * @return	人物模型
	 */
	public Integer getRoleModel() {
		return cS_NewRoleInfo.getRoleModel();
	}

	/**
	 * 
	 * @return	性别，true=男�??
	 */
	public Boolean getSex() {
		return cS_NewRoleInfo.getSex();
	}

	/**
	 * 
	 * @return	名称
	 */
	public String getName() {
		return cS_NewRoleInfo.getName();
	}

	public CS_NewRoleInfo getCS_NewRoleInfo() {
		return cS_NewRoleInfo;
	}

	@Override
	public byte[] getByteArray() {
		return cS_NewRoleInfo.toByteArray();
	}

}
