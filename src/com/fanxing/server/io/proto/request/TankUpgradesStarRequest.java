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
public class TankUpgradesStarRequest extends Request {

	private CS_TankUpgradesStar cS_TankUpgradesStar;

	public TankUpgradesStarRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_TankUpgradesStar cS_TankUpgradesStar) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_TankUpgradesStar = cS_TankUpgradesStar;

	}
	public TankUpgradesStarRequest(Request request, CS_TankUpgradesStar cS_TankUpgradesStar) {
		super(request);
		this.cS_TankUpgradesStar = cS_TankUpgradesStar;
	}

	public TankUpgradesStarRequest(IContent content, CS_TankUpgradesStar cS_TankUpgradesStar) {
		super(content);
		this.cS_TankUpgradesStar = cS_TankUpgradesStar;
	}

	/**
	 * 
	 * @return	坦克升级方式
	 */
	public ChangeWay getChangeWay() {
		return cS_TankUpgradesStar.getChangeWay();
	}

	/**
	 * 
	 * @return	坦克Id
	 */
	public Integer getTankId() {
		return cS_TankUpgradesStar.getTankId();
	}

	public CS_TankUpgradesStar getCS_TankUpgradesStar() {
		return cS_TankUpgradesStar;
	}

	@Override
	public byte[] getByteArray() {
		return cS_TankUpgradesStar.toByteArray();
	}

}
