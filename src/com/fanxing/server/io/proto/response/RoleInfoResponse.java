package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.fanxing.server.io.proto.protocol.StructProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class RoleInfoResponse extends Response {

	private SC_RoleInfo.Builder builder = SC_RoleInfo.newBuilder();

	public RoleInfoResponse(Request request) {
		super(request);
	}

	public SC_RoleInfo getSC_RoleInfo() {
		return builder.build();
	}

	/**
	 * 
	 * @param	exp
	 * 			经验
	 */
	public void setExp(Integer exp) {
		builder.setExp(exp);
	}

	/**
	 * 
	 * @param	oilMax
	 * 			�?��油料
	 */
	public void setOilMax(Integer oilMax) {
		builder.setOilMax(oilMax);
	}

	/**
	 * 
	 * @param	accountId
	 * 			帐号id
	 */
	public void setAccountId(Integer accountId) {
		builder.setAccountId(accountId);
	}

	/**
	 * 
	 * @param	level
	 * 			等级
	 */
	public void setLevel(Integer level) {
		builder.setLevel(level);
	}

	/**
	 * 
	 * @param	tankExp
	 * 			坦克经验
	 */
	public void setTankExp(Integer tankExp) {
		builder.setTankExp(tankExp);
	}

	/**
	 * 
	 * @param	registerDate
	 * 			注册时间
	 */
	public void setRegisterDate(String registerDate) {
		builder.setRegisterDate(registerDate);
	}

	/**
	 * 
	 * @param	name
	 * 			角色名称
	 */
	public void setName(String name) {
		builder.setName(name);
	}

	/**
	 * 
	 * @param	money
	 * 			游戏�?
	 */
	public void setMoney(Integer money) {
		builder.setMoney(money);
	}

	/**
	 * 
	 * @param	instanceId
	 * 			实例id
	 */
	public void setInstanceId(Integer instanceId) {
		builder.setInstanceId(instanceId);
	}

	/**
	 * 
	 * @param	loginDate
	 * 			�?��登录时间
	 */
	public void setLoginDate(String loginDate) {
		builder.setLoginDate(loginDate);
	}

	/**
	 * 
	 * @param	serverId
	 * 			服务器id
	 */
	public void setServerId(Integer serverId) {
		builder.setServerId(serverId);
	}

	/**
	 * 
	 * @param	gold
	 * 			元宝
	 */
	public void setGold(Integer gold) {
		builder.setGold(gold);
	}

	/**
	 * 
	 * @param	warehouse
	 * 			仓库
	 */
	public void setWarehouse(VO_Warehouse warehouse) {
		builder.setWarehouse(warehouse);
	}

	/**
	 * 
	 * @param	oil
	 * 			当前油料
	 */
	public void setOil(Integer oil) {
		builder.setOil(oil);
	}

	@Override
	protected byte[] buildDatas() {
		setSendMessageId0(getReceiveMessageId() + "Resp");
		setStatus(HTTP_STATUS_SUCCESS);
		return builder.build().toByteArray();
	}

	@Override
	public void mergeFrom(byte[] data) throws InvalidProtocolBufferException {
		builder.mergeFrom(data);
	}

	@Deprecated
	@Override
	public void setSendMessageId(String sendMessageId) {
		throw new UnsupportedOperationException();
	}

}
