package cc.lee.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by lizhitao on 16-3-8.
 * 无结果回调函数
 */
public abstract class JedisCallbackWithoutResult implements JedisCallback {
    public final Object callback(Jedis jedis) {
        callbackWithoutResult(jedis);
        return null;
    }

    protected abstract void callbackWithoutResult(Jedis jedis);
}
