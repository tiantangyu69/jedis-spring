package cc.lee.test.cli;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class SharedJedisPoolTest {
	@Test
	public void test() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo = new JedisShardInfo("localhost", "master", 6379, Protocol.DEFAULT_TIMEOUT, 1);
		shards.add(jedisShardInfo);

		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(new GenericObjectPoolConfig(), shards);
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set("key", "lizhtiao2");
			System.out.println(shardedJedis.get("key"));
		} catch (Exception e) {

		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		shardedJedisPool.destroy();
	}
}
