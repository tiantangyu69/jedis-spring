package cc.lee.redis.spring;

/**
 * Created by lizhitao on 16-3-8.
 */
public interface RedisClientFactory {
    <T> T create() throws Exception;
}
