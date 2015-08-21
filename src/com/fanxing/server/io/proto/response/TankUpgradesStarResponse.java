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
public class TankUpgradesStarResponse extends Response {

	private SC_TankUpgradesStar.Builder builder = SC_TankUpgradesStar.newBuilder();

	public TankUpgradesStarResponse(Request request) {
		super(request);
	}

	public SC_TankUpgradesStar getSC_TankUpgradesStar() {
		return builder.build();
	}

	/**
	 * 
	 * @param	tankId
	 * 			坦克升星后的Id
	 */
	public void setTankId(Integer tankId) {
		builder.setTankId(tankId);
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
