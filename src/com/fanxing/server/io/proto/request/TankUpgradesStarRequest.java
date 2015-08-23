package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.ServletContext;
import io.netty.channel.Channel;
import com.fanxing.server.io.proto.protocol.TankProtos.*;
import com.fanxing.server.io.proto.protocol.StructProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class TankUpgradesStarRequest extends Request {

	private CS_TankUpgradesStar cS_TankUpgradesStar;

	public TankUpgradesStarRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_TankUpgradesStar cS_TankUpgradesStar) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_TankUpgradesStar = cS_TankUpgradesStar;

	}
	public TankUpgradesStarRequest(Request request, CS_TankUpgradesStar cS_TankUpgradesStar) {
		super(request);
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
