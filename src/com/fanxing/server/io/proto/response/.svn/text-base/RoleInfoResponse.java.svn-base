package com.fanxing.server.io.proto.response;

import com.fanxing.server.io.proto.Response;
import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.fanxing.server.io.proto.protocol.StructProtos.*;
import java.lang.Iterable;

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
	 * @param	level
	 * 			等级
	 */
	public void setLevel(Integer level) {
		builder.setLevel(level);
	}

	/**
	 * 
	 * @param	sex
	 * 			性别，1为男性，0为女性
	 */
	public void setSex(Integer sex) {
		builder.setSex(sex);
	}

	/**
	 * 
	 * @param	loginDate
	 * 			最后登录时间
	 */
	public void setLoginDate(String loginDate) {
		builder.setLoginDate(loginDate);
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
	 * @param	tankTeams
	 * 			坦克队伍组
	 */
	public void setTankTeams(Iterable<VO_TankTeam> tankTeams) {
		builder.addAllTankTeams(tankTeams);
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
	 * @param	oilMax
	 * 			最大油料
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
	 * @param	instanceId
	 * 			实例id
	 */
	public void setInstanceId(Integer instanceId) {
		builder.setInstanceId(instanceId);
	}

	/**
	 * 
	 * @param	oil
	 * 			当前油料
	 */
	public void setOil(Integer oil) {
		builder.setOil(oil);
	}

	/**
	 * 
	 * @param	money
	 * 			游戏币
	 */
	public void setMoney(Integer money) {
		builder.setMoney(money);
	}

	/**
	 * 
	 * @param	roleModel
	 * 			角色模型id
	 */
	public void setRoleModel(Integer roleModel) {
		builder.setRoleModel(roleModel);
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
	 * @param	exp
	 * 			经验
	 */
	public void setExp(Integer exp) {
		builder.setExp(exp);
	}

	/**
	 * 
	 * @param	registerDate
	 * 			注册时间
	 */
	public void setRegisterDate(String registerDate) {
		builder.setRegisterDate(registerDate);
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
