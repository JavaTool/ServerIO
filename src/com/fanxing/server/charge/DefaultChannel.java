package com.fanxing.server.charge;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.fanxing.server.utils.DateUtil.formatDate;
import static java.lang.String.valueOf;
import static java.net.URLEncoder.encode;
import static java.text.MessageFormat.format;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fanxing.server.account.IAuthenticateAccount;

public class DefaultChannel extends HttpChannel {
	
	private static final String BALANCE_TEMPLATE = "?account={0}&appId={1}";
	
	private static final String PAY_TEMPLATE = "?account={0}&payItemInfo={1}&appId={2}";
	
	private static final String PAYCANCEL_TEMPLATE = "?account={0}&payCount={1}&payBillno={2}&appId={3}";
	
	private static final String PRESENT_TEMPLATE = "?account={0}&presentCount={1}&presentBillno={2}&appId={3}";
	
	private static final String CHARGE_TEMPLATE = "?account={0}&chargeCount={1}&appId={2}";
	
	private static final String QUERY_PAY_TEMPLATE = "?account={0}&appId={1}&startDate={2}&endDate={3}";

	@Override
	public JSONObject buy(IAuthenticateAccount account, PayItemInfo[] payItemInfos, String appId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getBalance(IAuthenticateAccount account, String appId) throws Exception {
		String info = encode(toJSONString(account), ENC);
		return getBalanceConnector().get(makeBalanceParam(info, appId));
	}
	
	private static String makeBalanceParam(String account, String appId) {
		return format(BALANCE_TEMPLATE, account, appId);
	}

	@Override
	public JSONObject pay(IAuthenticateAccount account, PayItemInfo payItemInfo, String appId) throws Exception {
		String info = encode(toJSONString(account), ENC);
		String payItem = encode(toJSONString(payItemInfo), ENC);
		return getPayConnector().get(makePayParam(info, payItem, appId));
	}
	
	private static String makePayParam(String account, String payItem, String appId) {
		return format(PAY_TEMPLATE, account, payItem, appId);
	}

	@Override
	public JSONObject payCancel(IAuthenticateAccount account, int amt, String billno, String appId) throws Exception {
		String info = encode(toJSONString(account), ENC);
		return getPayCancelConnector().get(makePayCancelParam(info, amt, billno, appId));
	}
	
	private static String makePayCancelParam(String account, int payCount, String billno, String appId) {
		return format(PAYCANCEL_TEMPLATE, account, valueOf(payCount), billno, appId);
	}

	@Override
	public JSONObject present(IAuthenticateAccount account, int count, String billno, String appId) throws Exception {
		String info = encode(toJSONString(account), ENC);
		return getPresentConnector().get(makePresentParam(info, count, billno, appId));
	}
	
	private static String makePresentParam(String account, int presentCount, String billno, String appId) {
		return format(PRESENT_TEMPLATE, account, valueOf(presentCount), billno, appId);
	}

	@Override
	public JSONObject charge(IAuthenticateAccount account, int count, String appId) throws Exception {
		String info = encode(toJSONString(account), ENC);
		return getChargeConnector().get(makeChargeParam(info, count, appId));
	}
	
	private static String makeChargeParam(String account, int chargeCount, String appId) {
		return format(CHARGE_TEMPLATE, account, valueOf(chargeCount), appId);
	}

	@Override
	public JSONObject queryPay(IAuthenticateAccount account, String appId, Date startDate, Date endDate) throws Exception {
		String info = encode(toJSONString(account), ENC);
		return getQueryPayConnector().get(makeQueryPayParam(info, appId, startDate, endDate));
	}
	
	private static String makeQueryPayParam(String account, String appId, Date startDate, Date endDate) throws Exception {
		return format(QUERY_PAY_TEMPLATE, account, appId, formatDateCanNull(startDate), formatDateCanNull(endDate));
	}
	
	private static String formatDateCanNull(Date date) throws Exception {
		return encode(date == null ? "" : formatDate(date), ENC);
	}

}
