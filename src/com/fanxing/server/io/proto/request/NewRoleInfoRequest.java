package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.http.HttpSession;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class NewRoleInfoRequest extends Request {

	private CS_NewRoleInfo cS_NewRoleInfo;

	public NewRoleInfoRequest(String ip, String receiveMessageId, HttpSession session, CS_NewRoleInfo cS_NewRoleInfo) {
		super(ip, receiveMessageId, session);
		this.cS_NewRoleInfo = cS_NewRoleInfo;

	}
	public NewRoleInfoRequest(Request request, CS_NewRoleInfo cS_NewRoleInfo) {
		super(request);
		this.cS_NewRoleInfo = cS_NewRoleInfo;
	}

	/**
	 * 
	 * @return	性别，true=男�?
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

	/**
	 * 
	 * @return	人物模型
	 */
	public Integer getRoleModel() {
		return cS_NewRoleInfo.getRoleModel();
	}

	public CS_NewRoleInfo getCS_NewRoleInfo() {
		return cS_NewRoleInfo;
	}

	@Override
	public byte[] getByteArray() {
		return cS_NewRoleInfo.toByteArray();
	}

}
