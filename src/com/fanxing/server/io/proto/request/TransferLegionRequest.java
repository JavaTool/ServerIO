package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TransferLegionRequest extends Request {

	private CS_TransferLegion cS_TransferLegion;

	public TransferLegionRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_TransferLegion cS_TransferLegion) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_TransferLegion = cS_TransferLegion;

	}
	public TransferLegionRequest(Request request, CS_TransferLegion cS_TransferLegion) {
		super(request);
		this.cS_TransferLegion = cS_TransferLegion;
	}

	public TransferLegionRequest(IContent content, CS_TransferLegion cS_TransferLegion) {
		super(content);
		this.cS_TransferLegion = cS_TransferLegion;
	}

	/**
	 * 
	 * @return	转让
	 */
	public Integer getTargetId() {
		return cS_TransferLegion.getTargetId();
	}

	public CS_TransferLegion getCS_TransferLegion() {
		return cS_TransferLegion;
	}

	@Override
	public byte[] getByteArray() {
		return cS_TransferLegion.toByteArray();
	}

}
