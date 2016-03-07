package cc.lee.redis;

import redis.clients.jedis.*;

import java.io.Closeable;

/**
 * Created by lizhitao on 16-3-7.
 * 统一对外redis接口
 */
public interface JredisCommands extends JedisCommands, MultiKeyCommands,
        AdvancedJedisCommands, ScriptingCommands, BasicCommands,
        ClusterCommands, SentinelCommands, BinaryJedisCommands,
        MultiKeyBinaryCommands, AdvancedBinaryJedisCommands,
        BinaryScriptingCommands, Closeable {
    void destory();
}