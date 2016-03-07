package cc.lee.test.cli;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {
	@Test
	public void testPool() {
		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("foo2", "lizhitao");
			System.out.println(jedis.get("foo2"));
		} finally {
			if (null != jedis) {jedis.close();}
		}
		jedisPool.destroy();
	}
}
