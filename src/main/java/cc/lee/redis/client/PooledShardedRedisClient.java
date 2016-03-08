package cc.lee.redis.client;

import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author lizhitao
 * PooledShardedRedisClient
 */
public abstract class PooledShardedRedisClient extends ShardedJedisPool implements ShardedRedisClient {
	public PooledShardedRedisClient(JedisPoolConfig poolConfig, List<JedisShardInfo> shards) {
		super(poolConfig, shards);
	}

    /**
     * 销毁连接池
     */
    public void destory() {
        super.destroy();
    }
}
