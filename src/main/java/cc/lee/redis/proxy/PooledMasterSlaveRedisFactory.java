package cc.lee.redis.proxy;

import cc.lee.redis.client.PooledRedisMasterSlaveClient;
import cc.lee.redis.client.RedisClient;
import cc.lee.redis.config.RedisClientConfig;
import redis.clients.jedis.JedisShardInfo;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by lizhitao on 16-3-8.
 */
public class PooledMasterSlaveRedisFactory {
    /**
     * 配置redis pool
     */
    private RedisClientConfig config;
    private List<JedisShardInfo> shardInfos;
    private RedisClient master;
    private RedisClient slave;

    protected final Constructor<PooledRedisMasterSlaveClient> constructor;

    public PooledMasterSlaveRedisFactory() {
        try {
            constructor = createConstructor();
            constructor.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建PooledRedisClient构造器并代理PooledRedisClient
     *
     * @return
     * @throws Exception
     */
    protected Constructor<PooledRedisMasterSlaveClient> createConstructor() throws Exception {
        return new PooledMasterSlaveRedisProxy().newProxy(PooledRedisMasterSlaveClient.class, false).getConstructor(RedisClient.class, RedisClient.class);
    }

    /**
     * 实例化创建PooledRedisClient对象
     *
     * @return
     * @throws Exception
     */
    public final synchronized PooledRedisMasterSlaveClient create() throws Exception {
        for (JedisShardInfo shardInfo : shardInfos) {
            if (shardInfo.getName().equals("master")) {
                RedisClientConfig masterConfig = (RedisClientConfig) config.clone();
                masterConfig.setDb(config.getDb());
                masterConfig.setPassword(shardInfo.getPassword());
                masterConfig.setHost(shardInfo.getHost());
                masterConfig.setPort(shardInfo.getPort());
                PooledRedisFactory masterFactory = new PooledRedisFactory();
                masterFactory.setRedisClientConfig(masterConfig);
                this.master = masterFactory.create();
            } else if (shardInfo.getName().equals("slave")) {
                RedisClientConfig slaverConfig = (RedisClientConfig) config.clone();
                slaverConfig.setDb(config.getDb());
                slaverConfig.setPassword(shardInfo.getPassword());
                slaverConfig.setHost(shardInfo.getHost());
                slaverConfig.setPort(shardInfo.getPort());
                PooledRedisFactory slaveFactory = new PooledRedisFactory();
                slaveFactory.setRedisClientConfig(slaverConfig);
                this.slave = slaveFactory.create();
            }
        }
        if (null == master || null == slave)
            throw new IllegalStateException("no exist master or slave");
        return constructor.newInstance(this.master, this.slave);
    }

    public void setRedisClientConfig(RedisClientConfig redisClientConfig) {
        this.config = redisClientConfig;
    }

    public void setShardInfos(List<JedisShardInfo> shardInfos) {
        this.shardInfos = shardInfos;
    }
}
