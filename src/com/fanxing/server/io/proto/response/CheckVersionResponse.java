package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.CommonProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import java.lang.Iterable;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class CheckVersionResponse extends Response {

	private SC_CheckVersion.Builder builder = SC_CheckVersion.newBuilder();

	public CheckVersionResponse(Request request) {
		super(request);
	}

	public SC_CheckVersion getSC_CheckVersion() {
		return builder.build();
	}

	/**
	 * 
	 * @param	updateUrl
	 * 			更新文件的链�?
	 */
	public void setUpdateUrl(String updateUrl) {
		builder.setUpdateUrl(updateUrl);
	}

	/**
	 * 
	 * @param	updateDb
	 * 			�?��更新的文件名称列�?
	 */
	public void setUpdateDb(Iterable<VO_FileVersion> updateDb) {
		builder.addAllUpdateDb(updateDb);
	}

	/**
	 * 
	 * @param	updateClient
	 * 			是否�?��更新客户�?
	 */
	public void setUpdateClient(Boolean updateClient) {
		builder.setUpdateClient(updateClient);
	}

	/**
	 * 
	 * @param	dbSize
	 * 			�?��更新的db文件总大�?
	 */
	public void setDbSize(Integer dbSize) {
		builder.setDbSize(dbSize);
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
