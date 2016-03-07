package cc.lee.redis;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author lizhitao
 */
public abstract class PooledShardedRedisClient extends ShardedJedisPool implements ShardedRedisClient {
	public PooledShardedRedisClient(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards) {
		super(poolConfig, shards);
	}
}
