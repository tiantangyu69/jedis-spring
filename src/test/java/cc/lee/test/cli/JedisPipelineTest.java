package cc.lee.test.cli;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class JedisPipelineTest {
	@Test
	public void test() {
		Jedis jedis = new Jedis();
		Pipeline pipeline = jedis.pipelined();
		for (int i = 0; i < 100; i++) {
			pipeline.lpush("list", "node" + i);
		}
		pipeline.sync();

		for (int i = 0; i < 100; i++) {
			System.out.println(jedis.rpop("list"));
		}
	}
}
