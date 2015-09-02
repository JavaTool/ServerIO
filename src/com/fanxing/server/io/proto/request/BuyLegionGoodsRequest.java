package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;
import java.util.List;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class BuyLegionGoodsRequest extends Request {

	private CS_BuyLegionGoods cS_BuyLegionGoods;

	public BuyLegionGoodsRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_BuyLegionGoods cS_BuyLegionGoods) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_BuyLegionGoods = cS_BuyLegionGoods;

	}
	public BuyLegionGoodsRequest(Request request, CS_BuyLegionGoods cS_BuyLegionGoods) {
		super(request);
		this.cS_BuyLegionGoods = cS_BuyLegionGoods;
	}

	public BuyLegionGoodsRequest(IContent content, CS_BuyLegionGoods cS_BuyLegionGoods) {
		super(content);
		this.cS_BuyLegionGoods = cS_BuyLegionGoods;
	}

	/**
	 * 
	 * @return	返回物品
	 */
	public List<VO_GoodsInfo> getGoodsInfosList() {
		return cS_BuyLegionGoods.getGoodsInfosList();
	}

	public CS_BuyLegionGoods getCS_BuyLegionGoods() {
		return cS_BuyLegionGoods;
	}

	@Override
	public byte[] getByteArray() {
		return cS_BuyLegionGoods.toByteArray();
	}

}
