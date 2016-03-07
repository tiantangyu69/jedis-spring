package cc.lee.redis.proxy;

import java.lang.reflect.Constructor;

import redis.clients.jedis.JedisPoolConfig;
import cc.lee.redis.PooledRedisClient;
import cc.lee.redis.config.RedisClientConfig;

/**
 * Created by lizhitao on 16-3-7.
 */
public class PooledRedisFactory {
	private RedisClientConfig config;

	protected final Constructor<PooledRedisClient> constructor;

	public PooledRedisFactory() {
		try {
			constructor = createConstructor();
			constructor.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    protected Constructor<PooledRedisClient> createConstructor() throws Exception {
        return new PooledRedisProxy().newProxy(PooledRedisClient.class, false).getConstructor(JedisPoolConfig.class, String.class, int.class, String.class, int.class);
    }
	public final synchronized PooledRedisClient create() throws Exception {
		return constructor.newInstance(config.getJedisPoolConfig(), config.getHost(), config.getPort(), config.getPassword(), config.getDb());
	}

	public void setRedisClientConfig(RedisClientConfig redisClientConfig) {
		this.config = redisClientConfig;
	}
}
