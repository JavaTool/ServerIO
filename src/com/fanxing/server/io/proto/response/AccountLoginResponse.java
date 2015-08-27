package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.AccountProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;
import java.lang.Iterable;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class AccountLoginResponse extends Response {

	private SC_AccountLogin.Builder builder = SC_AccountLogin.newBuilder();

	public AccountLoginResponse(Request request) {
		super(request);
	}

	public SC_AccountLogin getSC_AccountLogin() {
		return builder.build();
	}

	/**
	 * 
	 * @param	serverInfos
	 * 			服务器列表
	 */
	public void setServerInfos(Iterable<VO_ServerInfo> serverInfos) {
		builder.addAllServerInfos(serverInfos);
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
