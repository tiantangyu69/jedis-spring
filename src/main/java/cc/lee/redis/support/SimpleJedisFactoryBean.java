/**
 * 
 */
package cc.lee.redis.support;

import org.springframework.beans.factory.FactoryBean;

import redis.clients.jedis.Jedis;

/**
 * @author lizhitao
 *
 */
public class SimpleJedisFactoryBean extends SimpleJedisBeanFactory implements
		FactoryBean<Jedis> {
	@Override
	public Jedis getObject() throws Exception {
		return super.create();
	}

	@Override
	public Class<?> getObjectType() {
		return Jedis.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
