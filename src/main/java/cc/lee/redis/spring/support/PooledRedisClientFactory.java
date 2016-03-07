package cc.lee.redis.spring.support;

import cc.lee.redis.RedisClient;
import cc.lee.redis.config.RedisClientConfig;
import cc.lee.redis.proxy.PooledRedisFactory;

/**
 * Created by lizhitao on 16-3-7.
 */
public class PooledRedisClientFactory {
    private String addr;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxTotal;
    private Boolean testOnBorrow;
    private String password;
    private Integer db;
    private RedisClientConfig redisClientConfig = new RedisClientConfig();

    RedisClient create() throws Exception {
        return createCommands(redisClientConfig);
    }

    static RedisClient createCommands(RedisClientConfig config) throws Exception {
        PooledRedisFactory pooledRedisFactory = new PooledRedisFactory();
        pooledRedisFactory.setJedisPoolConfig(config.getJedisPoolConfig());
        pooledRedisFactory.setAddr(config.getAddr());
        pooledRedisFactory.setDb(config.getDb());
        pooledRedisFactory.setPassword(config.getPassword());
        return pooledRedisFactory.create();
    }

    public void setAddr(String addr) {
        this.addr = addr;
        redisClientConfig.setAddr(this.addr);
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
        redisClientConfig.getJedisPoolConfig().setTestOnBorrow(this.testOnBorrow);
    }

    public void setDb(Integer db) {
        this.db = db;
        redisClientConfig.setDb(db);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
