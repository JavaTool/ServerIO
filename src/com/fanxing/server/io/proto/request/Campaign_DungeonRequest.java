package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import javax.servlet.http.HttpSession;
import com.fanxing.server.io.proto.protocol.BattleProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class Campaign_DungeonRequest extends Request {

	private CS_Campaign_Dungeon cS_Campaign_Dungeon;

	public Campaign_DungeonRequest(String ip, String receiveMessageId, HttpSession session, CS_Campaign_Dungeon cS_Campaign_Dungeon) {
		super(ip, receiveMessageId, session);
		this.cS_Campaign_Dungeon = cS_Campaign_Dungeon;

	}
	public Campaign_DungeonRequest(Request request, CS_Campaign_Dungeon cS_Campaign_Dungeon) {
		super(request);
		this.cS_Campaign_Dungeon = cS_Campaign_Dungeon;
	}

	/**
	 * 
	 * @return	副本id
	 */
	public Integer getCampaignId() {
		return cS_Campaign_Dungeon.getCampaignId();
	}

	/**
	 * 
	 * @return	队伍id
	 */
	public Integer getTeamId() {
		return cS_Campaign_Dungeon.getTeamId();
	}

	public CS_Campaign_Dungeon getCS_Campaign_Dungeon() {
		return cS_Campaign_Dungeon;
	}

	@Override
	public byte[] getByteArray() {
		return cS_Campaign_Dungeon.toByteArray();
	}

}
