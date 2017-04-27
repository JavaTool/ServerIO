package org.tool.server.io.configuration;

import java.util.List;
import java.util.Map;

import org.tool.server.io.IConfigurationHolder;
import org.tool.server.io.IOParam;
import org.tool.server.io.http.client.HttpConnectorFactory;
import org.tool.server.io.http.client.IHttpConnector;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

/**
 * 
 * @author 	lisen
 */
public class ConfigurationManager implements IConfigurationManager {
	
	private final Table<String, String, String> configs;
	
	private IConfigurationHolder configurationHolder;
	
	private static final String PREKEY = "Config_";
	
	protected ConfigurationManager() {
		configs = HashBasedTable.create();
	}
	
	@Override
	public String getConfigValue(String configFileName, String key) {
		Map<String, String> map = configs.row(configFileName);
		return map != null ? map.get(key) : null;
	}

	@Override
	public String getConfigValue(String configFileName, String key, String defaultValue) {
		String value = getConfigurationValue(configFileName, key);
		return value == null ? defaultValue : value;
	}

	@Override
	public String getConfigurationValue(String key) {
		return configurationHolder!=null ? configurationHolder.getConfigurationValue(key) : null;
	}

	@Override
	public String getConfigurationValue(String key, String defaultValue) {
		return configurationHolder!=null ? configurationHolder.getConfigurationValue(key, defaultValue) : defaultValue;
	}
	
	public static ConfigurationManager createConfigManager(int serverId, String address, List<String> configNames, IConfigurationHolder configuration) throws Exception {
		ConfigurationManager configManager = new ConfigurationManager();
		configManager.configurationHolder = configuration;
		IHttpConnector<JSONObject> connector = HttpConnectorFactory.createJSONObject();
		connector.setUrl(address);
		for (String config : configNames) {
			Map<String, Object> params = Maps.newHashMap();
			params.put(PARAM_SERVERID, serverId);
			params.put(PARAM_CONFIG, config);
			JSONObject jsonObject = connector.get(params);
			if (jsonObject.getInteger(IOParam.RET_CODE) == IOParam.CODE_OK) {
				jsonObject.forEach((k,v) -> {
					if (k.startsWith(PREKEY)) {
						configManager.configs.put(config, k, (String)v);
					}
				});
			}
		}
		return configManager;
	}
}
