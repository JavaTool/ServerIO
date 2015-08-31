package com.fanxing.server.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;

import com.fanxing.server.cache.Cache;
import com.fanxing.server.utils.SerializaUtil;

public abstract class CacheOnJedis<B extends BinaryJedisCommands, J extends JedisCommands> implements Cache {
	
	protected static final Logger log = LoggerFactory.getLogger(CacheOnJedis.class);
	
	public abstract B getBinaryJedisCommands();
	
	public abstract void useFinish(B jedis);
	
	public abstract J getJedisCommands();
	
	public abstract void useFinish(J jedis);
	
	@Override
	public boolean exists(Serializable key) {
		B jedis = getBinaryJedisCommands();
		try {
			return jedis.exists(SerializaUtil.serializable(key));
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return false;
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public boolean hexists(Serializable key, Serializable name) {
		B jedis = getBinaryJedisCommands();
		try {
			return jedis.hexists(SerializaUtil.serializable(key), SerializaUtil.serializable(name));
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return false;
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void set(Serializable key, Serializable object) {
		B jedis = getBinaryJedisCommands();
		try {
			jedis.set(SerializaUtil.serializable(key), SerializaUtil.serializable(object));
		} catch (Exception e) {
			log.error("error on key " + key, e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void hset(Serializable key, Serializable name, Serializable object) {
		B jedis = getBinaryJedisCommands();
		try {
			jedis.hset(SerializaUtil.serializable(key), SerializaUtil.serializable(name), SerializaUtil.serializable(object));
		} catch (Exception e) {
			log.error("error on key " + key, e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void hmSet(Serializable key, Map<Serializable, Serializable> map) {
		B jedis = getBinaryJedisCommands();
		try {
			Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
			for (Serializable k : map.keySet()) {
				byteMap.put(SerializaUtil.serializable(k), SerializaUtil.serializable(map.get(k)));
			}
			jedis.hmset(SerializaUtil.serializable(key), byteMap);
		} catch (Exception e) {
			log.error("error on key " + key, e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void del(Serializable key) {
		B jedis = getBinaryJedisCommands();
		try {
			jedis.del(SerializaUtil.serializable(key));
		} catch (Exception e) {
			log.error("error on key " + key, e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public Serializable get(Serializable key) {
		B jedis = getBinaryJedisCommands();
		try {
			return SerializaUtil.deserializable(jedis.get(SerializaUtil.serializable(key)));
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return null;
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public Serializable hget(Serializable key, Serializable name) {
		B jedis = getBinaryJedisCommands();
		try {
			return SerializaUtil.deserializable(jedis.hget(SerializaUtil.serializable(key), SerializaUtil.serializable(name)));
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return null;
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public List<Serializable> hmGet(Serializable key, Serializable... names) {
		B jedis = getBinaryJedisCommands();
		try {
			byte[][] nameBytes = SerializaUtil.serializable(names);
			List<byte[]> list = jedis.hmget(SerializaUtil.serializable(key), nameBytes);
			return SerializaUtil.deserializable(list);
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return new ArrayList<Serializable>();
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void hdel(Serializable key, Serializable name) {
		B jedis = getBinaryJedisCommands();
		try {
			jedis.hdel(SerializaUtil.serializable(key), SerializaUtil.serializable(name));
		} catch (Exception e) {
			log.error("error on key " + key, e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void hmDel(Serializable key, Serializable... names) {
		B jedis = getBinaryJedisCommands();
		try {
			byte[][] nameBytes = SerializaUtil.serializable(names);
			for (byte[] name : nameBytes) {
				jedis.hdel(SerializaUtil.serializable(key), name);
			}
		} catch (Exception e) {
			log.error("error on key " + key, e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public long hlen(Serializable key) {
		B jedis = getBinaryJedisCommands();
		try {
			return jedis.hlen(SerializaUtil.serializable(key));
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return 0;
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void set(Serializable key, Serializable object, int timeout) {
		B jedis = getBinaryJedisCommands();
		try {
			jedis.setex(SerializaUtil.serializable(key), timeout, SerializaUtil.serializable(object));
		} catch (Exception e) {
			log.error("error on key " + key, e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public Map<Serializable, Serializable> hGetAll(Serializable key) {
		B jedis = getBinaryJedisCommands();
		try {
			Map<byte[], byte[]> all = jedis.hgetAll(SerializaUtil.serializable(key));
			Map<Serializable, Serializable> ret = new HashMap<Serializable, Serializable>();
			for (byte[] name : all.keySet()) {
				ret.put(SerializaUtil.deserializable(name), SerializaUtil.deserializable(all.get(name)));
			}
			return ret;
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return null;
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public Set<Serializable> hKeys(Serializable key) {
		B jedis = getBinaryJedisCommands();
		try {
			Set<byte[]> bytes = jedis.hkeys(SerializaUtil.serializable(key));
			Set<Serializable> keys = new HashSet<Serializable>();
			for (byte[] data : bytes) {
				keys.add(SerializaUtil.deserializable(data));
			}
			return keys;
		} catch (Exception e) {
			log.error("error on key " + key, e);
			return null;
		} finally {
			useFinish(jedis);
		}
	}

}
