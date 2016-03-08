package cc.lee.redis.proxy;

import java.lang.reflect.Constructor;

import redis.clients.jedis.JedisPoolConfig;
import cc.lee.redis.client.PooledRedisClient;
import cc.lee.redis.config.RedisClientConfig;

/**
 * Created by lizhitao on 16-3-7.
 * PooledRedisClient创建工厂
 */
public class PooledRedisFactory {
	/**
	 * 配置redis pool
	 */
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

	/**
	 * 创建PooledRedisClient构造器并代理PooledRedisClient
	 * @return
	 * @throws Exception
     */
    protected Constructor<PooledRedisClient> createConstructor() throws Exception {
        return new PooledRedisProxy().newProxy(PooledRedisClient.class, false).getConstructor(JedisPoolConfig.class, String.class, int.class, String.class, int.class);
    }

	/**
	 * 实例化创建PooledRedisClient对象
	 * @return
	 * @throws Exception
     */
	public final synchronized PooledRedisClient create() throws Exception {
		return constructor.newInstance(config.getJedisPoolConfig(), config.getHost(), config.getPort(), config.getPassword(), config.getDb());
	}

	public void setRedisClientConfig(RedisClientConfig redisClientConfig) {
		this.config = redisClientConfig;
	}
}
