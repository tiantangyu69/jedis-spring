package cc.lee.redis.spring;

import cc.lee.redis.RedisClient;
import cc.lee.redis.RedisMasterSlaveClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by lizhitao on 16-3-8.
 */
public class PooledMasterSlaveRedisClientFactoryBean extends PooledMasterSlaveClientFactory implements FactoryBean<RedisClient>, DisposableBean {
    private RedisMasterSlaveClient commands;

    public RedisClient getObject() throws Exception {
        return this.commands = super.create();
    }

    public Class<?> getObjectType() {
        return RedisMasterSlaveClient.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() throws Exception {
        if (null != commands)
            commands.destory();
    }
}
