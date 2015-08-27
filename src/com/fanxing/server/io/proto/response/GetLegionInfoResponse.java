package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetLegionInfoResponse extends Response {

	private SC_GetLegionInfo.Builder builder = SC_GetLegionInfo.newBuilder();

	public GetLegionInfoResponse(Request request) {
		super(request);
	}

	public SC_GetLegionInfo getSC_GetLegionInfo() {
		return builder.build();
	}

	/**
	 * 
	 * @param	LegionInfo
	 * 			军团信息
	 */
	public void setLegionInfo(VO_LegionInfo LegionInfo) {
		builder.setLegionInfo(LegionInfo);
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
