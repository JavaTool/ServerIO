package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import java.lang.Iterable;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountGetRoleListResponse extends Response {

	private SC_AccountGetRoleList.Builder builder = SC_AccountGetRoleList.newBuilder();

	public AccountGetRoleListResponse(Request request) {
		super(request);
	}

	public SC_AccountGetRoleList getSC_AccountGetRoleList() {
		return builder.build();
	}

	/**
	 * 
	 * @param	roles
	 * 			角色列表
	 */
	public void setRoles(Iterable<SC_RoleInfo> roles) {
		builder.addAllRoles(roles);
	}

	@Override
	protected byte[] buildDatas() {
		setSendMessageId0(getReceiveMessageId() + "Resp");
		setStatus(HTTP_STATUS_SUCCESS);
		return builder.build().toByteArray();
	}

	@Override
	public void mergeFrom(byte[] data) throws InvalidProtocolBufferException {
		builder.mergeFrom(data);
	}

	@Deprecated
	@Override
	public void setSendMessageId(String sendMessageId) {
		throw new UnsupportedOperationException();
	}

}
