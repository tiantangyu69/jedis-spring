package cc.lee.redis.client;

import cc.lee.redis.Destory;
import cc.lee.redis.JedisCallback;
import cc.lee.redis.JedisCallbackWithoutResult;
import redis.clients.jedis.*;

import java.io.Closeable;

/**
 * Created by lizhitao on 16-3-7. 统一对外redis接口
 */
public interface RedisClient extends JedisCommands, MultiKeyCommands,
		AdvancedJedisCommands, ScriptingCommands, BasicCommands,
		ClusterCommands, SentinelCommands, BinaryJedisCommands,
		MultiKeyBinaryCommands, AdvancedBinaryJedisCommands,
		BinaryScriptingCommands, Closeable, Destory {
	<T> T execute(JedisCallback callback);

	void execute(JedisCallbackWithoutResult callbackWithResult);

	Pipeline pipelined();
}
