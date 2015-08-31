package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GainWelfareResponse extends Response {

	private SC_GainWelfare.Builder builder = SC_GainWelfare.newBuilder();

	public GainWelfareResponse(Request request) {
		super(request);
	}

	public SC_GainWelfare getSC_GainWelfare() {
		return builder.build();
	}

	/**
	 * 
	 * @param	goodsInfos
	 * 			返回物品
	 */
	public void setGoodsInfos(VO_GoodsInfo goodsInfos) {
		builder.setGoodsInfos(goodsInfos);
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
