package cc.lee.redis.spring;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Protocol;
import cc.lee.redis.client.RedisClient;
import cc.lee.redis.config.RedisClientConfig;
import cc.lee.redis.proxy.PooledRedisFactory;

/**
 * Created by lizhitao on 16-3-7.
 */
public class PooledRedisClientFactory implements RedisClientFactory, InitializingBean {
    private String addr;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxTotal;
    private Boolean testOnBorrow;
    private String password;
    private Integer db;
    private RedisClientConfig redisClientConfig = new RedisClientConfig();

    public RedisClient create() throws Exception {
        return createCommands(redisClientConfig);
    }

    RedisClient createCommands(RedisClientConfig config)
            throws Exception {
        PooledRedisFactory pooledRedisFactory = new PooledRedisFactory();
        pooledRedisFactory.setRedisClientConfig(redisClientConfig);
        return pooledRedisFactory.create();
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public void setPassword(String password) {
        this.password = password;
        redisClientConfig.setPassword(this.password);
    }

    /**
     * 所有属性设置完成之后，检查合法性，如果不合法，设置默认值
     */
    public void afterPropertiesSet() throws Exception {
        // 设置host和port
        if (null == addr || addr.trim().equals("")) {
            redisClientConfig.setHost(Protocol.DEFAULT_HOST);
            redisClientConfig.setPort(Protocol.DEFAULT_PORT);
        } else {
            String[] hostAndPort = addr.split(":");
            if (hostAndPort.length == 2) {
                redisClientConfig.setHost(hostAndPort[0]);
                redisClientConfig.setPort(Integer.parseInt(hostAndPort[1]));
            } else {
                redisClientConfig.setHost(hostAndPort[0]);
                redisClientConfig.setPort(Protocol.DEFAULT_PORT);
            }
        }

        if (null == db) {
            redisClientConfig.setDb(0);
        }

        if (null == maxIdle) {
            redisClientConfig.getJedisPoolConfig().setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE);
        }

        if (null == maxTotal) {
            redisClientConfig.getJedisPoolConfig().setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL);
        }

        if (null == minIdle) {
            redisClientConfig.getJedisPoolConfig().setMaxIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE);
        }

        if (null == testOnBorrow) {
            redisClientConfig.getJedisPoolConfig().setTestOnBorrow(GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW);
        }
    }
}
