package cc.lee.test.cli;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class JedisTest {
	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("localhost");
		Transaction transaction = jedis.multi();
		transaction.set("foo", "cc.lee");
		transaction.set("foo2", "cc.lee2");
		transaction.exec();
		System.out.println(jedis.get("foo"));
	}
}
