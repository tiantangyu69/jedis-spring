package cc.lee.test.cli.spring.jedis;

import cc.lee.redis.RedisClient;
import cc.lee.test.cli.spring.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class SimpleJedisFactoryBeanTest extends BaseTest {
	@Resource
	private RedisClient redis;

	@Test
	public void testFactoryBean() {
		for (int i = 0; i < 10000; i++) {
			redis.set("foo", "lee");
			System.out.println(redis.get("foo"));
		}
	}
}
