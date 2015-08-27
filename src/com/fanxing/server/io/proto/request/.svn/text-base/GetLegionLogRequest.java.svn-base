package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class GetLegionLogRequest extends Request {

	private CS_GetLegionLog cS_GetLegionLog;

	public GetLegionLogRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_GetLegionLog cS_GetLegionLog) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_GetLegionLog = cS_GetLegionLog;

	}
	public GetLegionLogRequest(Request request, CS_GetLegionLog cS_GetLegionLog) {
		super(request);
		this.cS_GetLegionLog = cS_GetLegionLog;
	}

	public GetLegionLogRequest(IContent content, CS_GetLegionLog cS_GetLegionLog) {
		super(content);
		this.cS_GetLegionLog = cS_GetLegionLog;
	}

	/**
	 * 
	 * @return	军团id
	 */
	public Integer getLegionId() {
		return cS_GetLegionLog.getLegionId();
	}

	public CS_GetLegionLog getCS_GetLegionLog() {
		return cS_GetLegionLog;
	}

	@Override
	public byte[] getByteArray() {
		return cS_GetLegionLog.toByteArray();
	}

}
