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
public class TankUpgradeResponse extends Response {

	private SC_TankUpgrade.Builder builder = SC_TankUpgrade.newBuilder();

	public TankUpgradeResponse(Request request) {
		super(request);
	}

	public SC_TankUpgrade getSC_TankUpgrade() {
		return builder.build();
	}

	/**
	 * 
	 * @param	tankExp
	 * 			坦克经验
	 */
	public void setTankExp(Integer tankExp) {
		builder.setTankExp(tankExp);
	}

	/**
	 * 
	 * @param	roleTankExp
	 * 			角色表中的坦克经验
	 */
	public void setRoleTankExp(Integer roleTankExp) {
		builder.setRoleTankExp(roleTankExp);
	}

	/**
	 * 
	 * @param	spendResult
	 * 			消耗后的结果（货币，材料，道具）
	 */
	public void setSpendResult(VO_SpendResult spendResult) {
		builder.setSpendResult(spendResult);
	}

	/**
	 * 
	 * @param	tankLevel
	 * 			坦克升级后的等级
	 */
	public void setTankLevel(Integer tankLevel) {
		builder.setTankLevel(tankLevel);
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
