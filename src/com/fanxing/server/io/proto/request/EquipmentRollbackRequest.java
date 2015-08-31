package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class EquipmentRollbackRequest extends Request {

	private CS_EquipmentRollback cS_EquipmentRollback;

	public EquipmentRollbackRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_EquipmentRollback cS_EquipmentRollback) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_EquipmentRollback = cS_EquipmentRollback;

	}
	public EquipmentRollbackRequest(Request request, CS_EquipmentRollback cS_EquipmentRollback) {
		super(request);
		this.cS_EquipmentRollback = cS_EquipmentRollback;
	}

	public EquipmentRollbackRequest(IContent content, CS_EquipmentRollback cS_EquipmentRollback) {
		super(content);
		this.cS_EquipmentRollback = cS_EquipmentRollback;
	}

	/**
	 * 
	 * @return	精炼属�??
	 */
	public Integer getRefinAttr() {
		return cS_EquipmentRollback.getRefinAttr();
	}

	/**
	 * 
	 * @return	装备ID
	 */
	public Integer getEquipmentId() {
		return cS_EquipmentRollback.getEquipmentId();
	}

	public CS_EquipmentRollback getCS_EquipmentRollback() {
		return cS_EquipmentRollback;
	}

	@Override
	public byte[] getByteArray() {
		return cS_EquipmentRollback.toByteArray();
	}

}
