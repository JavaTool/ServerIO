package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.fanxing.server.io.proto.protocol.StructProtos.*;
import java.lang.Iterable;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class EquipmentRecastResponse extends Response {

	private SC_EquipmentRecast.Builder builder = SC_EquipmentRecast.newBuilder();

	public EquipmentRecastResponse(Request request) {
		super(request);
	}

	public SC_EquipmentRecast getSC_EquipmentRecast() {
		return builder.build();
	}

	/**
	 * 
	 * @param	vo_Attribute
	 * 			装备附加属�??
	 */
	public void setVo_Attribute(Iterable<VO_Attribute> vo_Attribute) {
		builder.addAllVoAttribute(vo_Attribute);
	}

	/**
	 * 
	 * @param	spendResult
	 * 			消�?�后的结果（货币，材料，道具�?
	 */
	public void setSpendResult(VO_SpendResult spendResult) {
		builder.setSpendResult(spendResult);
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
