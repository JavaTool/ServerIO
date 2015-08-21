package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.TankProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.fanxing.server.io.proto.protocol.StructProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TankStrengthenResponse extends Response {

	private SC_TankStrengthen.Builder builder = SC_TankStrengthen.newBuilder();

	public TankStrengthenResponse(Request request) {
		super(request);
	}

	public SC_TankStrengthen getSC_TankStrengthen() {
		return builder.build();
	}

	/**
	 * 
	 * @param	tankStrengthenLevel
	 * 			坦克强化后的等级
	 */
	public void setTankStrengthenLevel(Integer tankStrengthenLevel) {
		builder.setTankStrengthenLevel(tankStrengthenLevel);
	}

	/**
	 * 
	 * @param	spendResult
	 * 			消�?�后的结果（货币，材料，道具�?
	 */
	public void setSpendResult(VO_SpendResult spendResult) {
		builder.setSpendResult(spendResult);
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
