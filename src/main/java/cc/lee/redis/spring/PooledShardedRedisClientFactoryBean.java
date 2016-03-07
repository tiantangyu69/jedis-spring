package cc.lee.redis.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import cc.lee.redis.ShardedRedisClient;

public class PooledShardedRedisClientFactoryBean implements FactoryBean<ShardedRedisClient>, DisposableBean {
	private ShardedRedisClient commands;

	public void destroy() throws Exception {
		if (null != commands) {
			commands.destory();
		}
	}

	public ShardedRedisClient getObject() throws Exception {
		return null;
	}

	public Class<?> getObjectType() {
		return ShardedRedisClient.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
