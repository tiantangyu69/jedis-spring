package cc.lee.redis.spring;

import cc.lee.redis.ShardedRedisClient;
import cc.lee.redis.config.RedisClientConfig;
import cc.lee.redis.proxy.PooledShardedRedisFactory;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisShardInfo;

import java.util.List;

/**
 * Created by lizhitao on 16-3-8.
 */
public class PooledSharedRedisClientFactory implements RedisClientFactory, InitializingBean {
    private RedisClientConfig redisClientConfig = new RedisClientConfig();
    private List<JedisShardInfo> shardInfos;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxTotal;
    private Boolean testOnBorrow;
    private Integer db;

    public ShardedRedisClient create() throws Exception {
        return createCommands(redisClientConfig);
    }

    ShardedRedisClient createCommands(RedisClientConfig config)
            throws Exception {
        PooledShardedRedisFactory pooledRedisFactory = new PooledShardedRedisFactory();
        pooledRedisFactory.setRedisClientConfig(redisClientConfig);
        pooledRedisFactory.setShardInfos(shardInfos);
        return pooledRedisFactory.create();
    }

    public void setShardInfos(List<JedisShardInfo> shardInfos) {
        this.shardInfos = shardInfos;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
        redisClientConfig.getJedisPoolConfig().setMaxIdle(this.maxIdle);
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
        redisClientConfig.getJedisPoolConfig().setMinIdle(this.minIdle);
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
        redisClientConfig.getJedisPoolConfig().setMaxTotal(this.maxTotal);
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
        redisClientConfig.getJedisPoolConfig().setTestOnBorrow(
                this.testOnBorrow);
    }

    public void setDb(Integer db) {
        this.db = db;
        redisClientConfig.setDb(this.db);
    }

    public void afterPropertiesSet() throws Exception {

    }
}
