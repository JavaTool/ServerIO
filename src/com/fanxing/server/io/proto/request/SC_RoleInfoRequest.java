package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.RoleProtos.*;
import com.fanxing.server.io.proto.protocol.StructProtos.*;
import java.util.List;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class SC_RoleInfoRequest extends Request {

	private SC_RoleInfo sC_RoleInfo;

	public SC_RoleInfoRequest(String ip, String receiveMessageId, String sessionId, ISender sender, SC_RoleInfo sC_RoleInfo) {
		super(ip, receiveMessageId, sessionId, sender);
		this.sC_RoleInfo = sC_RoleInfo;

	}
	public SC_RoleInfoRequest(Request request, SC_RoleInfo sC_RoleInfo) {
		super(request);
		this.sC_RoleInfo = sC_RoleInfo;
	}

	public SC_RoleInfoRequest(IContent content, SC_RoleInfo sC_RoleInfo) {
		super(content);
		this.sC_RoleInfo = sC_RoleInfo;
	}

	/**
	 * 
	 * @return	等级
	 */
	public Integer getLevel() {
		return sC_RoleInfo.getLevel();
	}

	/**
	 * 
	 * @return	性别�?1为男性，0为女�?
	 */
	public Integer getSex() {
		return sC_RoleInfo.getSex();
	}

	/**
	 * 
	 * @return	�?后登录时�?
	 */
	public String getLoginDate() {
		return sC_RoleInfo.getLoginDate();
	}

	/**
	 * 
	 * @return	免费装备精炼次数
	 */
	public Integer getRefiningFree() {
		return sC_RoleInfo.getRefiningFree();
	}

	/**
	 * 
	 * @return	坦克经验
	 */
	public Integer getTankExp() {
		return sC_RoleInfo.getTankExp();
	}

	/**
	 * 
	 * @return	坦克队伍�?
	 */
	public List<VO_TankTeam> getTankTeamsList() {
		return sC_RoleInfo.getTankTeamsList();
	}

	/**
	 * 
	 * @return	仓库
	 */
	public VO_Warehouse getWarehouse() {
		return sC_RoleInfo.getWarehouse();
	}

	/**
	 * 
	 * @return	服务器id
	 */
	public Integer getServerId() {
		return sC_RoleInfo.getServerId();
	}

	/**
	 * 
	 * @return	元宝
	 */
	public Integer getGold() {
		return sC_RoleInfo.getGold();
	}

	/**
	 * 
	 * @return	�?大油�?
	 */
	public Integer getOilMax() {
		return sC_RoleInfo.getOilMax();
	}

	/**
	 * 
	 * @return	帐号id
	 */
	public Integer getAccountId() {
		return sC_RoleInfo.getAccountId();
	}

	/**
	 * 
	 * @return	实例id
	 */
	public Integer getInstanceId() {
		return sC_RoleInfo.getInstanceId();
	}

	/**
	 * 
	 * @return	当前油料
	 */
	public Integer getOil() {
		return sC_RoleInfo.getOil();
	}

	/**
	 * 
	 * @return	游戏�?
	 */
	public Integer getMoney() {
		return sC_RoleInfo.getMoney();
	}

	/**
	 * 
	 * @return	角色模型id
	 */
	public Integer getRoleModel() {
		return sC_RoleInfo.getRoleModel();
	}

	/**
	 * 
	 * @return	角色名称
	 */
	public String getName() {
		return sC_RoleInfo.getName();
	}

	/**
	 * 
	 * @return	经验
	 */
	public Integer getExp() {
		return sC_RoleInfo.getExp();
	}

	/**
	 * 
	 * @return	注册时间
	 */
	public String getRegisterDate() {
		return sC_RoleInfo.getRegisterDate();
	}

	public SC_RoleInfo getSC_RoleInfo() {
		return sC_RoleInfo;
	}

	@Override
	public byte[] getByteArray() {
		return sC_RoleInfo.toByteArray();
	}

}
