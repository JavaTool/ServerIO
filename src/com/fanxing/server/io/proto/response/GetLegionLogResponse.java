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
public class GetLegionLogResponse extends Response {

	private SC_GetLegionLog.Builder builder = SC_GetLegionLog.newBuilder();

	public GetLegionLogResponse(Request request) {
		super(request);
	}

	public SC_GetLegionLog getSC_GetLegionLog() {
		return builder.build();
	}

	/**
	 * 
	 * @param	LegionLogs
	 * 			军团日志
	 */
	public void setLegionLogs(Iterable<VO_LegionLog> LegionLogs) {
		builder.addAllLegionLogs(LegionLogs);
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
