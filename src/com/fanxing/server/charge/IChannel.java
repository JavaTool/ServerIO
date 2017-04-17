package com.fanxing.server.charge;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fanxing.server.account.IAuthenticateAccount;
import com.fanxing.server.io.IOParam;

public interface IChannel extends IOParam {
	
	String ENC = "utf-8";
	
	String URL_PARAM_ACCOUNT = "account";
	
	String URL_PARAM_PAY_TIEM_INFO = "payItemInfo";
	
	String URL_PARAM_CHARGE_COUNT = "chargeCount";
	
	String URL_PARAM_PRESENT_COUNT = "presentCount";
	
	String URL_PARAM_PRESENT_BILLNO = "presentBillno";
	
	String URL_PARAM_PAY_COUNT = "payCount";
	
	String URL_PARAM_PAY_BILLNO = "payBillno";
	
	String URL_PARAM_START_DATE = "startDate";
	
	String URL_PARAM_END_DATE = "endDate";
	
	String RET_PARAM_BALANCE = "balance";
	
	String RET_PARAM_BILLNO = "billno";
	
	String RET_PARAM_PAY_COUNT = "payCount";
	
	int CODE_NOT_ENOUGHT = 1004;
	
	void setBuyUrl(String buyUrl);
	
	JSONObject buy(IAuthenticateAccount account, PayItemInfo[] payItemInfos, String appId) throws Exception;
	
	void setBalanceUrl(String balanceUrl);
	
	JSONObject getBalance(IAuthenticateAccount account, String appId) throws Exception;
	
	void setPayUrl(String payUrl);
	
	JSONObject pay(IAuthenticateAccount account, PayItemInfo payItemInfo, String appId) throws Exception;
	
	void setPayCannelUrl(String payUrl);
	
	JSONObject payCancel(IAuthenticateAccount account, int amt, String billno, String appId) throws Exception;
	
	void setPresentUrl(String presentUrl);
	
	JSONObject present(IAuthenticateAccount account, int count, String billno, String appId) throws Exception;
	
	void setChargeUrl(String chargeUrl);
	
	JSONObject charge(IAuthenticateAccount account, int count, String appId) throws Exception;
	
	void setQueryPayUrl(String queryPayUrl);
	
	JSONObject queryPay(IAuthenticateAccount account, String appId, Date startDate, Date endDate) throws Exception;

}
