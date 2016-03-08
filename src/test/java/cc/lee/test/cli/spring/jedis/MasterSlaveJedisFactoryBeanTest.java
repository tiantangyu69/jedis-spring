package cc.lee.test.cli.spring.jedis;

import cc.lee.redis.JedisCallback;
import cc.lee.redis.JedisCallbackWithoutResult;
import cc.lee.redis.client.RedisMasterSlaveClient;
import cc.lee.test.cli.spring.BaseTest;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;

public class MasterSlaveJedisFactoryBeanTest extends BaseTest {
    @Resource
    private RedisMasterSlaveClient masterSlaveRedis;

    @Test
    public void testFactoryBean() {
        masterSlaveRedis.set("bbb", "bbb");
        System.out.println(masterSlaveRedis.get("bbb"));


        masterSlaveRedis.execute(new JedisCallback() {
            public Void callback(Jedis jedis) {
                Transaction transaction = jedis.multi();
                transaction.set("master-tran", "Mastar事务");
                transaction.set("master-tran2", "Mastar事务2");
                transaction.exec();
                return null;
            }
        });

        System.out.println(masterSlaveRedis.get("master-tran"));
        System.out.println(masterSlaveRedis.get("master-tran2"));

        masterSlaveRedis.execute(new JedisCallbackWithoutResult() {
            @Override
            protected void callbackWithoutResult(Jedis jedis) {
                Transaction transaction = jedis.multi();
                transaction.set("master-tran3", "Mastar事务3");
                transaction.set("master-tran4", "Mastar事务4");
                transaction.exec();
            }
        });

        System.out.println(masterSlaveRedis.get("master-tran3"));
        System.out.println(masterSlaveRedis.get("master-tran4"));
    }
}
