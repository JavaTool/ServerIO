package com.fanxing.server.io.proto;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.http.HttpStatus;

public class Response implements HttpStatus {
	
	protected static final Logger log = LoggerFactory.getLogger(Response.class);
	
	private static final byte[] NULL_SEND_DATAS = new byte[0];
	
	private int sendMessageId;
	
	private byte[] sendDatas;
	
	private int status;
	
	private Request request;
	
	public Response(Request request) {
		this.request = request;
		setStatus(HTTP_STATUS_SUCCESS);
		setSendDatas(NULL_SEND_DATAS);
	}
	
	public Response(String ip, ISender sender) {
		this(new Request(ip, 0, "", sender));
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

	public int getSendMessageId() {
		return sendMessageId;
	}

	public void setSendMessageId(int sendMessageId) {
		setSendMessageId0(sendMessageId);
	}

	protected void setSendMessageId0(int sendMessageId) {
		this.sendMessageId = sendMessageId;
	}
	
	public void build(long useTime, int serial) throws Exception {
		setSendDatas(buildDatas());
		setStatus(HTTP_STATUS_SUCCESS);
		request.getSender().send(getSendDatas(), serial, getSendMessageId(), useTime);
	}
	
	public void build(Collection<ISender> senders) throws Exception {
		setSendDatas(buildDatas());
		setStatus(HTTP_STATUS_SUCCESS);
		byte[] datas = getSendDatas();
		int messageId = getSendMessageId();
		for (ISender sender : senders) {
			sender.send(datas, 0, messageId, 0);
		}
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

	protected int getReceiveMessageId() {
		return request.getReceiveMessageId();
	}

}
