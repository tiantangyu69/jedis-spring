package cc.lee.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by lizhitao on 16-3-8.
 * 调用原生Jedis类操作
 */
public interface JedisCallback<Result> {
    Result callback(Jedis jedis);
}
