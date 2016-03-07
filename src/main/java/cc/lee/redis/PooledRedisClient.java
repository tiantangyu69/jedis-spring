package cc.lee.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * Created by lizhitao on 16-3-7.
 */
public abstract class PooledRedisClient extends JedisPool implements RedisClient {
	public PooledRedisClient(JedisPoolConfig poolConfig, String host, int port,
			String password, int db) {
		super(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, password, db);
	}

	public void destory() {
		super.destroy();
	}
}
