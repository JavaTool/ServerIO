package org.tool.server.push;

import static com.google.common.collect.Maps.newHashMap;
import static org.tool.server.io.IOParam.CODE_ERROR;
import static org.tool.server.io.IOParam.RET_CODE;
import static org.tool.server.io.IOParam.RET_MSG;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.http.client.HttpConnectorFactory;
import org.tool.server.io.http.client.IHttpConnector;

import com.alibaba.fastjson.JSONObject;

public abstract class RemotePush implements IPush {
	
	private static final Logger log = LoggerFactory.getLogger(RemotePush.class);
	
	private final IHttpConnector<JSONObject> pushAllConnector;
	
	private final IHttpConnector<JSONObject> pushAliasConnector;
	
	private final IHttpConnector<JSONObject> pushTagConnector;
	
	private final IHttpConnector<JSONObject> pushIdConnector;
	
	private String app;
	
	public RemotePush() {
		pushAllConnector = HttpConnectorFactory.createJSONObject();
		pushAliasConnector = HttpConnectorFactory.createJSONObject();
		pushTagConnector = HttpConnectorFactory.createJSONObject();
		pushIdConnector = HttpConnectorFactory.createJSONObject();
	}

	@Override
	public JSONObject pushAll(String msg) {
		Map<String, Object> params = createParams(msg);
		return push(params, pushAllConnector);
	}
	
	private JSONObject push(Map<String, Object> params, IHttpConnector<JSONObject> connector) {
		try {
			return pushAllConnector.get(params);
		} catch (Exception e) {
			return createError(e);
		}
	}
	
	private Map<String, Object> createParams(String msg) {
		Map<String, Object> params = newHashMap();
		params.put(PARAM_APP, app);
		params.put(PARAM_MSG, msg);
		return params;
	}
	
	private static JSONObject createError(Exception e) {
		log.error("", e);
		JSONObject json = new JSONObject();
		json.put(RET_CODE, CODE_ERROR);
		json.put(RET_MSG, e.getMessage());
		return json;
	}

	@Override
	public JSONObject pushAlias(String msg, String... aliases) {
		Map<String, Object> params = createParams(msg);
		params.put(PARAM_ALIAS, joinString(aliases));
		return push(params, pushAliasConnector);
	}
	
	private static String joinString(String... strs) {
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str).append(PARAM_SPLIT);
		}
		builder.setLength(builder.length() == 0 ? 0 : builder.length() - 1);
		return builder.toString();
	}

	@Override
	public JSONObject pushTag(String msg, String... tags) {
		Map<String, Object> params = createParams(msg);
		params.put(PARAM_TAG, joinString(tags));
		return push(params, pushTagConnector);
	}

	@Override
	public JSONObject pushId(String msg, String... ids) {
		Map<String, Object> params = createParams(msg);
		params.put(PARAM_ID, joinString(ids));
		return push(params, pushIdConnector);
	}
	
	private Map<String, Object> createScheduleParams(String time, String msg) {
		Map<String, Object> params = createParams(msg);
		params.put(PARAM_TIME, time);
		return params;
	}

	@Override
	public JSONObject schedulePushAll(String time, String msg) {
		Map<String, Object> params = createScheduleParams(time, msg);
		return push(params, pushAllConnector);
	}

	@Override
	public JSONObject schedulePushAlias(String time, String msg, String... aliases) {
		Map<String, Object> params = createScheduleParams(time, msg);
		params.put(PARAM_ALIAS, joinString(aliases));
		return push(params, pushAliasConnector);
	}

	@Override
	public JSONObject schedulePushTag(String time, String msg, String... tags) {
		Map<String, Object> params = createScheduleParams(time, msg);
		params.put(PARAM_TAG, joinString(tags));
		return push(params, pushTagConnector);
	}

	@Override
	public JSONObject schedulePushId(String time, String msg, String... ids) {
		Map<String, Object> params = createScheduleParams(time, msg);
		params.put(PARAM_ID, joinString(ids));
		return push(params, pushIdConnector);
	}

	@Deprecated
	@Override
	public final String[] getApps() {
		return null;
	}
	
	protected final void setApp(String app) {
		this.app = app;
	}
	
	protected final void setUrl(String url) {
		pushAliasConnector.setUrl(url + "/push/alias");
		pushAllConnector.setUrl(url + "/push/all");
		pushIdConnector.setUrl(url + "/push/id");
		pushTagConnector.setUrl(url + "/push/tag");
	}

}
