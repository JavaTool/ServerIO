package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.TestProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class Test1Response extends Response {

	private SC_Test1.Builder builder = SC_Test1.newBuilder();

	public Test1Response(Request request) {
		super(request);
	}

	public SC_Test1 getSC_Test1() {
		return builder.build();
	}

	/**
	 * 
	 * @param	a
	 * 			<u>do not has any annotate.</u>
	 */
	public void setA(Integer a) {
		builder.setA(a);
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
