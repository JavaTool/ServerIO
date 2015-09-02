package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class EquipmentInheritRequest extends Request {

	private CS_EquipmentInherit cS_EquipmentInherit;

	public EquipmentInheritRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_EquipmentInherit cS_EquipmentInherit) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_EquipmentInherit = cS_EquipmentInherit;

	}
	public EquipmentInheritRequest(Request request, CS_EquipmentInherit cS_EquipmentInherit) {
		super(request);
		this.cS_EquipmentInherit = cS_EquipmentInherit;
	}

	public EquipmentInheritRequest(IContent content, CS_EquipmentInherit cS_EquipmentInherit) {
		super(content);
		this.cS_EquipmentInherit = cS_EquipmentInherit;
	}

	/**
	 * 
	 * @return	精炼属�??
	 */
	public Integer getRefinAttr() {
		return cS_EquipmentInherit.getRefinAttr();
	}

	/**
	 * 
	 * @return	装备ID
	 */
	public Integer getEquipmentId() {
		return cS_EquipmentInherit.getEquipmentId();
	}

	/**
	 * 
	 * @return	被继承准备ID
	 */
	public Integer getEquipmentIdBe() {
		return cS_EquipmentInherit.getEquipmentIdBe();
	}

	public CS_EquipmentInherit getCS_EquipmentInherit() {
		return cS_EquipmentInherit;
	}

	@Override
	public byte[] getByteArray() {
		return cS_EquipmentInherit.toByteArray();
	}

}
