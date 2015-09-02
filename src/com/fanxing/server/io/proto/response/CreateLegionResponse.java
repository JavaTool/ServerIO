package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class CreateLegionResponse extends Response {

	private SC_CreateLegion.Builder builder = SC_CreateLegion.newBuilder();

	public CreateLegionResponse(Request request) {
		super(request);
	}

	public SC_CreateLegion getSC_CreateLegion() {
		return builder.build();
	}

	/**
	 * 
	 * @param	LegionId
	 * 			军团id
	 */
	public void setLegionId(Integer LegionId) {
		builder.setLegionId(LegionId);
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
