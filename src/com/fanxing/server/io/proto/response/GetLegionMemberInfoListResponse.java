package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import java.lang.Iterable;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetLegionMemberInfoListResponse extends Response {

	private SC_GetLegionMemberInfoList.Builder builder = SC_GetLegionMemberInfoList.newBuilder();

	public GetLegionMemberInfoListResponse(Request request) {
		super(request);
	}

	public SC_GetLegionMemberInfoList getSC_GetLegionMemberInfoList() {
		return builder.build();
	}

	/**
	 * 
	 * @param	legionMemberInfos
	 * 			军团成员
	 */
	public void setLegionMemberInfos(Iterable<VO_LegionMemberInfo> legionMemberInfos) {
		builder.addAllLegionMemberInfos(legionMemberInfos);
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
