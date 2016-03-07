package cc.lee.test.cli.spring.jedis;

import cc.lee.redis.JredisCommands;
import cc.lee.test.cli.spring.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class SimpleJedisFactoryBeanTest extends BaseTest {
	@Resource
	private JredisCommands redis;

	@Test
	public void testFactoryBean() {
		redis.set("foo", "lee");
		System.out.println(redis.get("foo"));
	}
}
