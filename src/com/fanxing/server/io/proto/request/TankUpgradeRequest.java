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
public class TankUpgradeRequest extends Request {

	private CS_TankUpgrade cS_TankUpgrade;

	public TankUpgradeRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel, CS_TankUpgrade cS_TankUpgrade) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.cS_TankUpgrade = cS_TankUpgrade;

	}
	public TankUpgradeRequest(Request request, CS_TankUpgrade cS_TankUpgrade) {
		super(request);
		this.cS_TankUpgrade = cS_TankUpgrade;
	}

	/**
	 * 
	 * @return	坦克升级方式
	 */
	public ChangeWay getChangeWay() {
		return cS_TankUpgrade.getChangeWay();
	}

	/**
	 * 
	 * @return	坦克升级类型
	 */
	public ChangeType getChangeType() {
		return cS_TankUpgrade.getChangeType();
	}

	/**
	 * 
	 * @return	坦克Id
	 */
	public Integer getTankId() {
		return cS_TankUpgrade.getTankId();
	}

	public CS_TankUpgrade getCS_TankUpgrade() {
		return cS_TankUpgrade;
	}

	@Override
	public byte[] getByteArray() {
		return cS_TankUpgrade.toByteArray();
	}

}
