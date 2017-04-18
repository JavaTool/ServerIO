package org.tool.server.cache.object.hash;

import static org.tool.server.utils.DateUtil.getMilliSecondStartOfDayRefresh;

import org.tool.server.cache.manager.ICacheManager;

public class DayRefreshObjectHash<K, F, V> extends ExpireObjectHash<K, F, V> {

	public DayRefreshObjectHash(ICacheManager cacheManager, Class<F> fclz, Class<V> vclz, String preKey) {
		super(cacheManager, fclz, vclz, preKey);
	}

	@Override
	protected long getExpireTime(V value) {
		return getMilliSecondStartOfDayRefresh();
	}

}
