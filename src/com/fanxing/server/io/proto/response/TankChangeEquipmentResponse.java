package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.TankProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TankChangeEquipmentResponse extends Response {

	private SC_TankChangeEquipment.Builder builder = SC_TankChangeEquipment.newBuilder();

	public TankChangeEquipmentResponse(Request request) {
		super(request);
	}

	public SC_TankChangeEquipment getSC_TankChangeEquipment() {
		return builder.build();
	}

	/**
	 * 
	 * @param	bagEquipId
	 * 			背包装备ID
	 */
	public void setBagEquipId(Integer bagEquipId) {
		builder.setBagEquipId(bagEquipId);
	}

	/**
	 * 
	 * @param	equipPos
	 * 			装备部位
	 */
	public void setEquipPos(Integer equipPos) {
		builder.setEquipPos(equipPos);
	}

	/**
	 * 
	 * @param	equipmentId
	 * 			装备ID
	 */
	public void setEquipmentId(Integer equipmentId) {
		builder.setEquipmentId(equipmentId);
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
