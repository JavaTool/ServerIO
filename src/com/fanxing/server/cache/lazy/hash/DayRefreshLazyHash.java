package com.fanxing.server.cache.lazy.hash;

import static com.fanxing.server.utils.DateUtil.getMilliSecondStartOfDayRefresh;

import com.fanxing.server.cache.manager.ICachePlatform;

public class DayRefreshLazyHash<F, V> extends ExpireLazyHash<F, V> {

	public DayRefreshLazyHash(ICachePlatform cachePlatform, Class<F> fclz, Class<V> vclz, String preKey) {
		super(cachePlatform, fclz, vclz, preKey);
	}

	@Override
	protected long getExpireTime(V value) {
		return getMilliSecondStartOfDayRefresh();
	}

}
