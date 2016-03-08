package cc.lee.redis.client;

import java.io.Closeable;

import cc.lee.redis.Destory;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;
import redis.clients.util.ShardInfo;

/**
 * @author lizhitao
 * sharedredis统一对外接口
 */
public interface ShardedRedisClient extends JedisCommands, BinaryJedisCommands,
		Closeable, Destory {
    ShardInfo getShardInfo(String key);
}
