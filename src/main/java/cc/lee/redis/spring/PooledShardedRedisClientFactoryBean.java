package cc.lee.redis.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import cc.lee.redis.client.ShardedRedisClient;

/**
 * Created by lizhitao on 16-3-8.
 * ShardedRedisClient FactoryBean
 */
public class PooledShardedRedisClientFactoryBean extends PooledSharedRedisClientFactory implements FactoryBean<ShardedRedisClient>, DisposableBean {
    private ShardedRedisClient commands;

    public ShardedRedisClient getObject() throws Exception {
        return this.commands = super.create();
    }

    public Class<?> getObjectType() {
        return ShardedRedisClient.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() throws Exception {
        if (null != commands) {
            commands.destory();
        }
    }

}
