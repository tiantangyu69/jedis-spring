package cc.lee.test.cli;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("localhost");
		jedis.set("foo", "cc.lee");
		System.out.println(jedis.get("foo"));
	}
}
