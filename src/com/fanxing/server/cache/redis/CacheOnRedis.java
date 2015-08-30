package com.fanxing.server.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
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
public class CacheOnRedis extends CacheOnJedis<Jedis, Jedis> implements Cache, Counter {
	
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
	public Jedis getBinaryJedisCommands() {
		return jedis;
	}

	@Override
	public void mDel(Serializable... keys) {
		try {
			byte[][] keyBytes = SerializaUtil.serializable(keys);
			getBinaryJedisCommands().del(keyBytes);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public List<Serializable> mGet(Serializable... keys) {
		try {
			byte[][] keyBytes = SerializaUtil.serializable(keys);
			List<byte[]> list = getBinaryJedisCommands().mget(keyBytes);
			return SerializaUtil.deserializable(list);
		} catch (Exception e) {
			log.error("", e);
			return new ArrayList<Serializable>();
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
			getBinaryJedisCommands().mset(keysvalues);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void clear() {
		getBinaryJedisCommands().flushAll();
		log.info("clear {}.", host);
	}

	@Deprecated
	@Override
	public void useFinish(Jedis jedis) {}

	@Override
	public Jedis getJedisCommands() {
		return jedis;
	}

}
