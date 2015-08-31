package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.TankProtos.*;
import com.fanxing.server.io.proto.protocol.StructProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TankStrengthenRequest extends Request {

	private CS_TankStrengthen cS_TankStrengthen;

	public TankStrengthenRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_TankStrengthen cS_TankStrengthen) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_TankStrengthen = cS_TankStrengthen;

	}
	public TankStrengthenRequest(Request request, CS_TankStrengthen cS_TankStrengthen) {
		super(request);
		this.cS_TankStrengthen = cS_TankStrengthen;
	}

	public TankStrengthenRequest(IContent content, CS_TankStrengthen cS_TankStrengthen) {
		super(content);
		this.cS_TankStrengthen = cS_TankStrengthen;
	}

	/**
	 * 
	 * @return	坦克升级方式
	 */
	public ChangeWay getChangeWay() {
		return cS_TankStrengthen.getChangeWay();
	}

	/**
	 * 
	 * @return	坦克Id
	 */
	public Integer getTankId() {
		return cS_TankStrengthen.getTankId();
	}

	public CS_TankStrengthen getCS_TankStrengthen() {
		return cS_TankStrengthen;
	}

	@Override
	public byte[] getByteArray() {
		return cS_TankStrengthen.toByteArray();
	}

}
