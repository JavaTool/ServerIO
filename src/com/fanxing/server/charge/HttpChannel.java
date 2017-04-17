package com.fanxing.server.charge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.fanxing.server.io.http.client.HttpConnectorFactory;
import com.fanxing.server.io.http.client.IHttpConnector;

public abstract class HttpChannel implements IChannel {
	
	protected static final Logger log = LoggerFactory.getLogger(HttpChannel.class);
	
	private final IHttpConnector<JSONObject> buyConnector;
	
	private final IHttpConnector<JSONObject> balanceConnector;
	
	private final IHttpConnector<JSONObject> payConnector;
	
	private final IHttpConnector<JSONObject> payCancelConnector;
	
	private final IHttpConnector<JSONObject> presentConnector;
	
	private final IHttpConnector<JSONObject> chargeConnector;
	
	private final IHttpConnector<JSONObject> queryPayConnector;
	
	public HttpChannel() {
		buyConnector = HttpConnectorFactory.createJSONObject();
		balanceConnector = HttpConnectorFactory.createJSONObject();
		payConnector = HttpConnectorFactory.createJSONObject();
		payCancelConnector = HttpConnectorFactory.createJSONObject();
		presentConnector = HttpConnectorFactory.createJSONObject();
		chargeConnector = HttpConnectorFactory.createJSONObject();
		queryPayConnector = HttpConnectorFactory.createJSONObject();
	}
	
	protected IHttpConnector<JSONObject> getBuyConnector() {
		return buyConnector;
	}
	
	protected IHttpConnector<JSONObject> getBalanceConnector() {
		return balanceConnector;
	}
	
	protected IHttpConnector<JSONObject> getPayConnector() {
		return payConnector;
	}
	
	protected IHttpConnector<JSONObject> getPayCancelConnector() {
		return payCancelConnector;
	}
	
	protected IHttpConnector<JSONObject> getPresentConnector() {
		return presentConnector;
	}
	
	protected IHttpConnector<JSONObject> getChargeConnector() {
		return chargeConnector;
	}
	
	protected IHttpConnector<JSONObject> getQueryPayConnector() {
		return queryPayConnector;
	}

	@Override
	public void setBuyUrl(String buyUrl) {
		getBuyConnector().setUrl(buyUrl);
	}

	@Override
	public void setBalanceUrl(String balanceUrl) {
		getBalanceConnector().setUrl(balanceUrl);
	}

	@Override
	public void setPayUrl(String payUrl) {
		getPayConnector().setUrl(payUrl);
	}

	@Override
	public void setPayCannelUrl(String payCannelUrl) {
		getPayCancelConnector().setUrl(payCannelUrl);
	}

	@Override
	public void setPresentUrl(String presentUrl) {
		getPresentConnector().setUrl(presentUrl);
	}

	@Override
	public void setChargeUrl(String chargeUrl) {
		getChargeConnector().setUrl(chargeUrl);
	}

	@Override
	public void setQueryPayUrl(String queryPayUrl) {
		getQueryPayConnector().setUrl(queryPayUrl);
	}

}
