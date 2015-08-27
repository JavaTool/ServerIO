package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class DonateGoodsRequest extends Request {

	private CS_DonateGoods cS_DonateGoods;

	public DonateGoodsRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_DonateGoods cS_DonateGoods) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_DonateGoods = cS_DonateGoods;

	}
	public DonateGoodsRequest(Request request, CS_DonateGoods cS_DonateGoods) {
		super(request);
		this.cS_DonateGoods = cS_DonateGoods;
	}

	public DonateGoodsRequest(IContent content, CS_DonateGoods cS_DonateGoods) {
		super(content);
		this.cS_DonateGoods = cS_DonateGoods;
	}

	/**
	 * 
	 * @return	捐献数量
	 */
	public Integer getCount() {
		return cS_DonateGoods.getCount();
	}

	/**
	 * 
	 * @return	捐献物品静态表id
	 */
	public Integer getKeyId() {
		return cS_DonateGoods.getKeyId();
	}

	public CS_DonateGoods getCS_DonateGoods() {
		return cS_DonateGoods;
	}

	@Override
	public byte[] getByteArray() {
		return cS_DonateGoods.toByteArray();
	}

}
