package com.fanxing.server.io.proto;

import com.fanxing.server.io.proto.protocol.StructProtos.VO_Error;
import com.fanxing.server.word.ErrorInfo;

public class Response implements HTTPStatus, ErrorInfo {
	
	private static final byte[] NULL_SEND_DATAS = new byte[0];
	
	private String sendMessageId;
	
	private byte[] sendDatas;
	
	private int status;
	
	private Request request;
	
	private VO_Error.Builder errorBuilder;
	
	public Response(Request request) {
		this.request = request;
		setStatus(HTTP_STATUS_SUCCESS);
		setSendDatas(NULL_SEND_DATAS);
	}

	public void mergeFrom(byte[] data) throws Exception {
		setSendDatas(data);
	}

	public byte[] getSendDatas() {
		return sendDatas;
	}

	protected void setSendDatas(byte[] sendDatas) {
		this.sendDatas = sendDatas;
	}

	public String getSendMessageId() {
		return sendMessageId;
	}

	public void setSendMessageId(String sendMessageId) {
		setSendMessageId0(sendMessageId);
	}

	protected void setSendMessageId0(String sendMessageId) {
		this.sendMessageId = sendMessageId;
	}
	
	public void build() {
		setSendDatas(hasError() ? errorBuilder.build().toByteArray() : buildDatas());
		setStatus(HTTP_STATUS_SUCCESS);
		setSendMessageId0(hasError() ? "MIVO_Error" : request.getReceiveMessageId() + "Resp");
	}
	
	protected byte[] buildDatas() {
		return getSendDatas();
	}

	public int getStatus() {
		return status;
	}

	protected void setStatus(int status) {
		this.status = status;
	}

	@Override
	public void setError(int errorCode, String errorMsg) {
		errorBuilder = VO_Error.newBuilder();
		errorBuilder.setErrorCode(errorCode);
		errorBuilder.setErrorMsg(errorMsg);
	}

	@Override
	public void setError(String errorMsg) {
		setError(NORMAL, errorMsg);
	}

	protected String getReceiveMessageId() {
		return request.getReceiveMessageId();
	}

	@Override
	public boolean hasError() {
		return errorBuilder != null;
	}

}
