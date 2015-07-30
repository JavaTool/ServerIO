package persist.impl.cache;

import java.util.HashMap;

import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.config.Configuration;
import org.jboss.cache.config.Configuration.CacheMode;
import org.jboss.cache.config.EvictionConfig;
import org.jboss.cache.config.EvictionRegionConfig;
import org.jboss.cache.eviction.LRUAlgorithmConfig;
import org.jboss.cache.lock.IsolationLevel;

import persist.impl.Cache;

public class JBossCacheCache implements Cache {
	
	org.jboss.cache.Cache<Object, Object> cache;
	
	protected HashMap<String, Fqn<String>> fqns = null;
	
	public JBossCacheCache() {
		Configuration config = new Configuration();
		config.setCacheMode(CacheMode.LOCAL);
		config.setIsolationLevel(IsolationLevel.NONE);
		
		EvictionConfig ec = new EvictionConfig();
		EvictionRegionConfig erc = new EvictionRegionConfig();
		LRUAlgorithmConfig lru = new LRUAlgorithmConfig();
		lru.setMaxNodes(1000);
		erc.setRegionName("_default_");
		erc.setEvictionAlgorithmConfig(new LRUAlgorithmConfig());
		
		ec.setDefaultEvictionRegionConfig(erc);
		config.setEvictionConfig(ec);
		
		CacheFactory<Object, Object> factory = new DefaultCacheFactory<Object, Object>();
		cache = factory.createCache(config);
		
		fqns = new HashMap<String, Fqn<String>>();
	}

	@Override
	public void createGroup(String group) {
		Fqn<String> fqn = Fqn.fromString(group);
		cache.getRoot().addChild(fqn);
		fqns.put(group, fqn);
	}

	@Override
	public void put(String group, Object key, Object entity) {
		cache.put(fqns.get(group), key, entity);
	}

	@Override
	public Object get(String group, Object key) {
		return cache.get(fqns.get(group), key);
	}

	@Override
	public boolean remove(String group, Object key) {
		return cache.remove(fqns.get(group), key) != null;
	}

}
