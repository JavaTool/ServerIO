package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.http.HttpSession;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class ReconnectRequest extends Request {

	private CS_Reconnect cS_Reconnect;

	public ReconnectRequest(String ip, String receiveMessageId, HttpSession session, CS_Reconnect cS_Reconnect) {
		super(ip, receiveMessageId, session);
		this.cS_Reconnect = cS_Reconnect;

	}
	public ReconnectRequest(Request request, CS_Reconnect cS_Reconnect) {
		super(request);
		this.cS_Reconnect = cS_Reconnect;
	}

	/**
	 * 
	 * @return	帐号名称
	 */
	public String getAccount() {
		return cS_Reconnect.getAccount();
	}

	/**
	 * 
	 * @return	服务器id
	 */
	public Integer getServerId() {
		return cS_Reconnect.getServerId();
	}

	/**
	 * 
	 * @return	密码
	 */
	public String getPassword() {
		return cS_Reconnect.getPassword();
	}

	public CS_Reconnect getCS_Reconnect() {
		return cS_Reconnect;
	}

	@Override
	public byte[] getByteArray() {
		return cS_Reconnect.toByteArray();
	}

}
