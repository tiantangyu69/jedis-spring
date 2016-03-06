/**
 * 
 */
package cc.lee.redis.support;

import org.springframework.beans.factory.FactoryBean;

import redis.clients.jedis.JedisCommands;

/**
 * @author lizhitao
 *
 */
public class SimpleJedisFactoryBean extends SimpleJedisBeanFactory implements
		FactoryBean<JedisCommands> {
	@Override
	public JedisCommands getObject() throws Exception {
		return super.create();
	}

	@Override
	public Class<?> getObjectType() {
		return JedisCommands.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
