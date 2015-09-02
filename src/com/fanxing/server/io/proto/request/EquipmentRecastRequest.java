package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.EquipmentProtos.*;
import java.util.List;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class EquipmentRecastRequest extends Request {

	private CS_EquipmentRecast cS_EquipmentRecast;

	public EquipmentRecastRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_EquipmentRecast cS_EquipmentRecast) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_EquipmentRecast = cS_EquipmentRecast;

	}
	public EquipmentRecastRequest(Request request, CS_EquipmentRecast cS_EquipmentRecast) {
		super(request);
		this.cS_EquipmentRecast = cS_EquipmentRecast;
	}

	public EquipmentRecastRequest(IContent content, CS_EquipmentRecast cS_EquipmentRecast) {
		super(content);
		this.cS_EquipmentRecast = cS_EquipmentRecast;
	}

	/**
	 * 
	 * @return	å±žæ?§ID
	 */
	public List<Integer> getAttrIdList() {
		return cS_EquipmentRecast.getAttrIdList();
	}

	/**
	 * 
	 * @return	è£…å¤‡ID
	 */
	public Integer getEquipmentId() {
		return cS_EquipmentRecast.getEquipmentId();
	}

	public CS_EquipmentRecast getCS_EquipmentRecast() {
		return cS_EquipmentRecast;
	}

	@Override
	public byte[] getByteArray() {
		return cS_EquipmentRecast.toByteArray();
	}

}
