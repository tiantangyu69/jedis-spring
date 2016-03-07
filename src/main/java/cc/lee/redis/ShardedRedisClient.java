package cc.lee.redis;

import java.io.Closeable;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;

public interface ShardedRedisClient extends JedisCommands, BinaryJedisCommands,
		Closeable, Destory {
}
