package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class HttpCheckResponse extends Response {

	private SC_HttpCheck.Builder builder = SC_HttpCheck.newBuilder();

	public HttpCheckResponse(Request request) {
		super(request);
	}

	public SC_HttpCheck getSC_HttpCheck() {
		return builder.build();
	}

	/**
	 * 
	 * @param	MessageId
	 * 			<u>do not has any annotate.</u>
	 */
	public void setMessageId(String MessageId) {
		builder.setMessageId(MessageId);
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
