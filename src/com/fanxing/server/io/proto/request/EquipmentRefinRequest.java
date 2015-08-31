package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class EquipmentRefinRequest extends Request {

	private CS_EquipmentRefin cS_EquipmentRefin;

	public EquipmentRefinRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_EquipmentRefin cS_EquipmentRefin) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_EquipmentRefin = cS_EquipmentRefin;

	}
	public EquipmentRefinRequest(Request request, CS_EquipmentRefin cS_EquipmentRefin) {
		super(request);
		this.cS_EquipmentRefin = cS_EquipmentRefin;
	}

	public EquipmentRefinRequest(IContent content, CS_EquipmentRefin cS_EquipmentRefin) {
		super(content);
		this.cS_EquipmentRefin = cS_EquipmentRefin;
	}

	/**
	 * 
	 * @return	装备ID
	 */
	public Integer getEquipmentId() {
		return cS_EquipmentRefin.getEquipmentId();
	}

	public CS_EquipmentRefin getCS_EquipmentRefin() {
		return cS_EquipmentRefin;
	}

	@Override
	public byte[] getByteArray() {
		return cS_EquipmentRefin.toByteArray();
	}

}
