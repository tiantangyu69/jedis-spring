package cc.lee.redis.proxy;

import cc.lee.redis.PooledRedisClient;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Constructor;

/**
 * Created by lizhitao on 16-3-7.
 */
public class PooledRedisFactory {
    private JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    private String addr;
    private Integer db;
    private String password;

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
        return constructor.newInstance(jedisPoolConfig, addr, 6379, password, db);
    }


    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setDb(Integer db) {
        this.db = db;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
