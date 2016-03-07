package cc.lee.redis.spring.support;

import cc.lee.redis.RedisClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by lizhitao on 16-3-7.
 * 链接池redis client
 */
public class PooledRedisClientFactoryBean extends PooledRedisClientFactory implements FactoryBean<RedisClient>, DisposableBean {
    private RedisClient commands;

    public RedisClient getObject() throws Exception {
        return this.commands = super.create();
    }

    public Class<?> getObjectType() {
        return RedisClient.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() throws Exception {
        if (null != this.commands) {
            this.commands.destory();
        }
    }
}
