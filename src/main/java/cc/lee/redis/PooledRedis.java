package cc.lee.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lizhitao on 16-3-7.
 */
public abstract class PooledRedis extends JedisPool implements JredisCommands {
    public PooledRedis(JedisPoolConfig poolConfig, String host, int port, String password, int db) {
        super(poolConfig, host, port, 0, password, db);
    }

    public void destory() {
        super.destroy();
    }
}
