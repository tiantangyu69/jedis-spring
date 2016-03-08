package cc.lee.redis.client;

import cc.lee.redis.JedisCallback;
import cc.lee.redis.JedisCallbackWithoutResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizhitao on 16-3-8.
 * 主从复制架构的redis client
 */
public abstract class PooledRedisMasterSlaveClient implements RedisMasterSlaveClient {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected RedisClient master;
    protected RedisClient slave;

    public PooledRedisMasterSlaveClient(RedisClient master, RedisClient slave) {
        this.master = master;
        this.slave = slave;
    }

    /**
     * 执行Jedis原生API
     * @param callback
     * @param <T>
     * @return
     */
    public <T> T execute(JedisCallback callback) {
        return master.execute(callback);
    }

    /**
     * 执行Jedis原生API无返回结果
     * @param callbackWithResult
     */
    public void execute(JedisCallbackWithoutResult callbackWithResult){
        master.execute(callbackWithResult);
    }

    /**
     * 销毁连接池
     */
    public void destory() {
        master.destory();
        slave.destory();
    }
}
