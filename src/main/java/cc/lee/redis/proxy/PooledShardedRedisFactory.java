package cc.lee.redis.proxy;

import cc.lee.redis.client.PooledShardedRedisClient;
import cc.lee.redis.config.RedisClientConfig;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by lizhitao on 16-3-8.
 */
public class PooledShardedRedisFactory {
    /**
     * 配置shardedredis pool
     */
    private RedisClientConfig config;
    private List<JedisShardInfo> shardInfos;

    protected final Constructor<PooledShardedRedisClient> constructor;

    public PooledShardedRedisFactory() {
        try {
            constructor = createConstructor();
            constructor.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建PooledShardedRedisClient构造器并代理PooledShardedRedisClient
     *
     * @return
     * @throws Exception
     */
    protected Constructor<PooledShardedRedisClient> createConstructor() throws Exception {
        return new PooledShardedRedisProxy().newProxy(PooledShardedRedisClient.class, false).getConstructor(JedisPoolConfig.class, List.class);
    }

    /**
     * 实例化创建PooledShardedRedisClient对象
     *
     * @return
     * @throws Exception
     */
    public final synchronized PooledShardedRedisClient create() throws Exception {
        return constructor.newInstance(config.getJedisPoolConfig(), shardInfos);
    }

    public void setRedisClientConfig(RedisClientConfig redisClientConfig) {
        this.config = redisClientConfig;
    }

    public void setShardInfos(List<JedisShardInfo> shardInfos) {
        this.shardInfos = shardInfos;
    }
}
