package com.fanxing.server.cache.redis;

import redis.clients.jedis.Jedis;

public interface IJedisReources {
	
	public static interface RedisExecutor<T> {
		
		T exec(Jedis jedis) throws Exception;
		
	}
	
	public static interface VoidRedisExecutor {
		
		void exec(Jedis jedis) throws Exception;
		
	}
	
	<T> T exec(RedisExecutor<T> run, T param);
	
	void exec(VoidRedisExecutor run);
	
	void changeDB(int index);
	
	boolean ping();

}
