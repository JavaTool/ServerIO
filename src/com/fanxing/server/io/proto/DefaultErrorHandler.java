package com.fanxing.server.io.proto;

import java.util.Map;

import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;
import com.fanxing.server.io.proto.protocol.StructProtos.VO_Error;

public class DefaultErrorHandler implements IErrorHandler {
	
	private static final int ERROR = MessageId.MIVO_Error.getNumber();

	private Map<String, Integer> errorCodes;
	
	public DefaultErrorHandler(Map<String, Integer> errorCodes) {
		this.errorCodes = errorCodes;
	}

	@Override
	public Response createErrorResponse(Request request, String error) throws Exception {
		String msg = error.contains(":") ? error.split(":")[0].trim() : error;
		VO_Error.Builder builder = VO_Error.newBuilder();
		builder.setErrorCode(errorCodes.containsKey(msg) ? errorCodes.get(msg) : 0);
		builder.setErrorMsg(msg);
		
		Response response = new Response(request);
		response.setSendMessageId(ERROR);
		response.mergeFrom(builder.build().toByteArray());
		return response;
	}

}
