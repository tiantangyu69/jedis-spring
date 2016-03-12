package cc.lee.test.cli.spring.jedis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;

import redis.clients.jedis.Pipeline;
import cc.lee.redis.client.RedisClient;
import cc.lee.test.cli.spring.BaseTest;

public class RedisListTest extends BaseTest {
	@Resource
	private RedisClient redis;
	private ExecutorService threadPool = Executors.newFixedThreadPool(100);
	private CountDownLatch latch = new CountDownLatch(1000000);

	@Test
	public void testList() throws InterruptedException {
		/*Pipeline pipeline = redis.pipelined();
		for (int i = 0; i < 1000000; i++) {
			pipeline.lpush("list", "node:" + i);
		}
		pipeline.sync();*/

		for (int i = 0; i < 1000000; i++) {
			threadPool.execute(new Runnable() {
				public void run() {
					System.out.println(redis.rpop("list"));
					latch.countDown();
				}
			});
		}
		latch.await();
		threadPool.shutdown();
	}
}
