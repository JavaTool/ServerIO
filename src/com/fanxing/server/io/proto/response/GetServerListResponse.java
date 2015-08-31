package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import java.lang.Iterable;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetServerListResponse extends Response {

	private SC_GetServerList.Builder builder = SC_GetServerList.newBuilder();

	public GetServerListResponse(Request request) {
		super(request);
	}

	public SC_GetServerList getSC_GetServerList() {
		return builder.build();
	}

	/**
	 * 
	 * @param	serverInfos
	 * 			服务器列�?
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
