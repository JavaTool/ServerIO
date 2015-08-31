package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.fanxing.server.io.proto.protocol.StructProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class EquipmentRollbackResponse extends Response {

	private SC_EquipmentRollback.Builder builder = SC_EquipmentRollback.newBuilder();

	public EquipmentRollbackResponse(Request request) {
		super(request);
	}

	public SC_EquipmentRollback getSC_EquipmentRollback() {
		return builder.build();
	}

	/**
	 * 
	 * @param	spendResult
	 * 			æ¶è?åçç»æï¼è´§å¸ï¼ææï¼éå·ï¼?
	 */
	public void setSpendResult(VO_SpendResult spendResult) {
		builder.setSpendResult(spendResult);
	}

	/**
	 * 
	 * @param	equipmentId
	 * 			è£å¤ID
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
