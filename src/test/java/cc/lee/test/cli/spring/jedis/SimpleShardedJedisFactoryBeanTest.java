package cc.lee.test.cli.spring.jedis;

import cc.lee.redis.client.ShardedRedisClient;
import cc.lee.test.cli.spring.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class SimpleShardedJedisFactoryBeanTest extends BaseTest {
    @Resource
    private ShardedRedisClient shardedJedis;

    @Test
    public void testFactoryBean() {
        shardedJedis.set("key", "lizhtiao");
        System.out.println(shardedJedis.getShardInfo("key").getName() + "," + shardedJedis.get("key"));
        shardedJedis.set("key2", "lizhtiao2");
        System.out.println(shardedJedis.getShardInfo("key2").getName() + "," + shardedJedis.get("key2"));
        shardedJedis.set("key3", "lizhtiao3");
        System.out.println(shardedJedis.getShardInfo("key3").getName() + "," + shardedJedis.get("key3"));
        shardedJedis.set("key4", "lizhtiao4");
        System.out.println(shardedJedis.getShardInfo("key4").getName() + "," + shardedJedis.get("key4"));

        shardedJedis.del("key");
        shardedJedis.del("key2");
        shardedJedis.del("key3");
        shardedJedis.del("key4");
    }
}
