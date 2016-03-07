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
		JedisShardInfo jedisShardInfo2 = new JedisShardInfo("localhost", "slave", 6380, Protocol.DEFAULT_TIMEOUT, 1);
		shards.add(jedisShardInfo);
		shards.add(jedisShardInfo2);

		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(new GenericObjectPoolConfig(), shards);
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set("key", "lizhtiao");
			System.out.println(shardedJedis.getShardInfo("key") +";"+shardedJedis.get("key"));
			shardedJedis.set("key2", "lizhtiao2");
			System.out.println(shardedJedis.getShardInfo("key2") +";"+shardedJedis.get("key2"));
			shardedJedis.set("key3", "lizhtiao3");
			System.out.println(shardedJedis.getShardInfo("key3") +";"+shardedJedis.get("key3"));
			shardedJedis.set("key4", "lizhtiao4");
			System.out.println(shardedJedis.getShardInfo("key4") +";"+shardedJedis.get("key4"));
			
		} catch (Exception e) {

		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		shardedJedisPool.destroy();
	}
}
