package com.fanxing.server.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.fanxing.server.cache.Cache;
import com.fanxing.server.io.ConfigurationHolder;
import com.fanxing.server.sequence.Counter;
import com.fanxing.server.utils.SerializaUtil;

/**
 * Redis缓存器
 * @author 	fuhuiyuan
 */
public class CacheOnRedis implements Cache, Counter {
	
	protected static final Logger log = LoggerFactory.getLogger(CacheOnRedis.class);
	
	protected Jedis jedis;
	/**Redis信息*/
	protected RedisHost host;
	
	public CacheOnRedis(ConfigurationHolder configuration) {
		this(configuration.getConfigurationValue("cache_redisHosts"));
	}
	
	public CacheOnRedis(String redisHostContent) {
		String[] hostInfos = redisHostContent.split(":");
		host = new RedisHost(hostInfos[0], Integer.parseInt(hostInfos[1]));
		jedis = new Jedis(host.getHost(), host.getPort());
		log.info("Redis connect, {}.", jedis.toString());
	}

	@Override
	public void set(Serializable key, Serializable object) {
		try {
			jedis.set(SerializaUtil.serializable(key), SerializaUtil.serializable(object));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void hset(Serializable key, Serializable name, Serializable object) {
		try {
			jedis.hset(SerializaUtil.serializable(key), SerializaUtil.serializable(name), SerializaUtil.serializable(object));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void mSet(Map<Serializable, Serializable> map) {
		try {
			int mapSize = map.size();
			byte[][] keysvalues = new byte[mapSize << 1][];
			int index = 0;
			for (Serializable key : map.keySet()) {
				keysvalues[index << 1] = SerializaUtil.serializable(key);
				keysvalues[(index << 1) + 1] = SerializaUtil.serializable(map.get(key));
				index++;
			}
			jedis.mset(keysvalues);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void hmSet(Serializable key, Map<Serializable, Serializable> map) {
		try {
			Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
			for (Serializable k : map.keySet()) {
				byteMap.put(SerializaUtil.serializable(k), SerializaUtil.serializable(map.get(k)));
			}
			jedis.hmset(SerializaUtil.serializable(key), byteMap);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void del(Serializable key) {
		try {
			jedis.del(SerializaUtil.serializable(key));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public Serializable get(Serializable key) {
		try {
			return SerializaUtil.deserializable(jedis.get(SerializaUtil.serializable(key)));
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}

	@Override
	public Serializable hget(Serializable key, Serializable name) {
		try {
			return SerializaUtil.deserializable(jedis.hget(SerializaUtil.serializable(key), SerializaUtil.serializable(name)));
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}

	@Override
	public List<Serializable> mGet(Serializable... keys) {
		try {
			byte[][] keyBytes = SerializaUtil.serializable(keys);
			List<byte[]> list = jedis.mget(keyBytes);
			return SerializaUtil.deserializable(list);
		} catch (Exception e) {
			log.error("", e);
			return new ArrayList<Serializable>();
		}
	}

	@Override
	public List<Serializable> hmGet(Serializable key, Serializable... names) {
		try {
			byte[][] nameBytes = SerializaUtil.serializable(names);
			List<byte[]> list = jedis.hmget(SerializaUtil.serializable(key), nameBytes);
			return SerializaUtil.deserializable(list);
		} catch (Exception e) {
			log.error("", e);
			return new ArrayList<Serializable>();
		}
	}

	@Override
	public void clear() {
		jedis.flushAll();
		log.info("clear : " + host.toString());
	}

	@Override
	public void hdel(Serializable key, Serializable name) {
		try {
			jedis.hdel(SerializaUtil.serializable(key), SerializaUtil.serializable(name));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void mDel(Serializable... keys) {
		try {
			byte[][] keyBytes = SerializaUtil.serializable(keys);
			jedis.del(keyBytes);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void hmDel(Serializable key, Serializable... names) {
		try {
			byte[][] nameBytes = SerializaUtil.serializable(names);
			jedis.hdel(SerializaUtil.serializable(key), nameBytes);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public long get(String key) {
		return Long.parseLong(jedis.get(key));
	}

	@Override
	public long incr(String key, long value) {
		return jedis.incrBy(key, value);
	}

	@Override
	public long decr(String key, long value) {
		return jedis.decrBy(key, value);
	}

	@Override
	public void delete(String key) {
		jedis.del(key);
	}

	@Override
	public long hlen(Serializable key) {
		try {
			return jedis.hlen(SerializaUtil.serializable(key));
		} catch (Exception e) {
			log.error("", e);
			return 0;
		}
	}

}
