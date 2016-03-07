package cc.lee.test.cli;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ShardedJedis;

public class SharedJedisTest {
	@Test
	public void test() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo = new JedisShardInfo("localhost", "master", 6379, Protocol.DEFAULT_TIMEOUT, 1);
		shards.add(jedisShardInfo);
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		shardedJedis.set("foo1", "lizhitao");
		shardedJedis.close();
	}
}
