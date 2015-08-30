package com.fanxing.server.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.fanxing.server.cache.Cache;
import com.fanxing.server.io.ConfigurationHolder;
import com.fanxing.server.sequence.Counter;

/**
 * Redis组缓存器
 * @author 	fuhuiyuan
 */
public class CacheOnShardedRedis extends CacheOnJedis<ShardedJedis, ShardedJedis> implements Cache, Counter {
	
	protected static final Logger log = LoggerFactory.getLogger(CacheOnShardedRedis.class);
	
	protected ShardedJedisPool jedis;
	/**Redis主机信息集合*/
	protected Collection<RedisHost> redisHosts;
	
	public CacheOnShardedRedis(ConfigurationHolder configuration) {
		this(configuration.getConfigurationValue("cache_redisHosts"), Integer.parseInt(configuration.getConfigurationValue("cache_redisMaxConnections")));
	}
	
	/**
	 * 设置Redis主机信息集合
	 * @param 	redisHostContent
	 * 			Redis主机信息
	 * @param 	max
	 * 			最大连接数
	 */
    protected void setRedisHosts(String redisHostContent, int max){
        StringTokenizer st = new StringTokenizer(redisHostContent, ",");
        int fieldsCount = st.countTokens();
        Collection<RedisHost> hosts = new ArrayList<RedisHost>(fieldsCount);
        for (int i = 0; i < fieldsCount; i++){
            String fullHost = st.nextToken().trim();
            String[] hostAndPort = fullHost.split(":");
            hosts.add(new RedisHost(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }
        this.redisHosts = hosts;

		JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(max);
        List<JedisShardInfo> jedisShards = new ArrayList<JedisShardInfo>();
        for(RedisHost redisHost : redisHosts){
            jedisShards.add(new JedisShardInfo(redisHost.getHost(), redisHost.getPort()));
        }
        
        jedis = new ShardedJedisPool(poolConfig, jedisShards);
    }
	
	public CacheOnShardedRedis(String redisHostContent, int max) {
		setRedisHosts(redisHostContent, max);
		log.info("Redis connect, {}.", jedis.toString());
	}

	@Override
	public void mSet(Map<Serializable, Serializable> map) {
		for (Serializable key : map.keySet()) {
			set(key, map.get(key));
		}
	}

	@Override
	public List<Serializable> mGet(Serializable... keys) {
		int size = keys.length;
		List<Serializable> list = new ArrayList<Serializable>(size);
		for (int i = 0;i < keys.length;i++) {
			list.add(get(keys[i]));
		}
		return list;
	}

	@Override
	public void clear() {
		for (RedisHost redisHost : redisHosts) {
			new Jedis(redisHost.getHost(), redisHost.getPort()).flushAll();
			log.info("clear : {}", redisHost.toString());
		}
	}

	@Override
	public void mDel(Serializable... keys) {
		int size = keys.length;
		for (int i = 0;i < size;i++) {
			del(keys[i]);
		}
	}

	@Override
	public long get(String key) {
		ShardedJedis sharded = jedis.getResource();
		try {
			return Long.parseLong(sharded.get(key));
		} catch (Exception e) {
			log.error("", e);
			return 0;
		} finally {
			jedis.returnResourceObject(sharded);
		}
	}

	@Override
	public long incr(String key, long value) {
		ShardedJedis sharded = jedis.getResource();
		try {
			return sharded.incrBy(key, value);
		} finally {
			jedis.returnResourceObject(sharded);
		}
	}

	@Override
	public long decr(String key, long value) {
		ShardedJedis sharded = jedis.getResource();
		try {
			return sharded.decrBy(key, value);
		} finally {
			jedis.returnResourceObject(sharded);
		}
	}

	@Override
	public void delete(String key) {
		ShardedJedis sharded = jedis.getResource();
		try {
			sharded.del(key);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			jedis.returnResourceObject(sharded);
		}
	}

	@Override
	public ShardedJedis getBinaryJedisCommands() {
		return jedis.getResource();
	}

	@Override
	public void useFinish(ShardedJedis jedis) {
		this.jedis.returnResourceObject(jedis);
	}

	@Override
	public ShardedJedis getJedisCommands() {
		return jedis.getResource();
	}

}
