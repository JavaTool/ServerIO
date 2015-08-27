package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.TankProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TankChangeEquipmentRequest extends Request {

	private CS_TankChangeEquipment cS_TankChangeEquipment;

	public TankChangeEquipmentRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_TankChangeEquipment cS_TankChangeEquipment) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_TankChangeEquipment = cS_TankChangeEquipment;

	}
	public TankChangeEquipmentRequest(Request request, CS_TankChangeEquipment cS_TankChangeEquipment) {
		super(request);
		this.cS_TankChangeEquipment = cS_TankChangeEquipment;
	}

	public TankChangeEquipmentRequest(IContent content, CS_TankChangeEquipment cS_TankChangeEquipment) {
		super(content);
		this.cS_TankChangeEquipment = cS_TankChangeEquipment;
	}

	/**
	 * 
	 * @return	坦克ID
	 */
	public Integer getTankId() {
		return cS_TankChangeEquipment.getTankId();
	}

	/**
	 * 
	 * @return	装备ID
	 */
	public Integer getEquipmentId() {
		return cS_TankChangeEquipment.getEquipmentId();
	}

	public CS_TankChangeEquipment getCS_TankChangeEquipment() {
		return cS_TankChangeEquipment;
	}

	@Override
	public byte[] getByteArray() {
		return cS_TankChangeEquipment.toByteArray();
	}

}
