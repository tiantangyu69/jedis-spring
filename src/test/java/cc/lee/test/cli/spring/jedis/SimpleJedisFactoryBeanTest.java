package cc.lee.test.cli.spring.jedis;

import javax.annotation.Resource;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import cc.lee.test.cli.spring.BaseTest;

public class SimpleJedisFactoryBeanTest extends BaseTest {
	@Resource
	private Jedis jedis;

	@Test
	public void testFactoryBean() {
		jedis.set("foo", "lee");
		System.out.println(jedis.get("foo"));
	}
}
