package cc.lee.test.cli.spring.jedis;

import cc.lee.redis.RedisMasterSlaveClient;
import cc.lee.redis.ShardedRedisClient;
import cc.lee.test.cli.spring.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class MasterSlaveJedisFactoryBeanTest extends BaseTest {
    @Resource
    private RedisMasterSlaveClient masterSlaveRedis;

    @Test
    public void testFactoryBean() {
        masterSlaveRedis.set("aaaa", "aaaaa");
        System.out.println(masterSlaveRedis.get("aaaa"));
    }
}
