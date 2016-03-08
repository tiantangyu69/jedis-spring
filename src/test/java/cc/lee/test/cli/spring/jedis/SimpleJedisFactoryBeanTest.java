package cc.lee.test.cli.spring.jedis;

import cc.lee.redis.JedisCallback;
import cc.lee.redis.JedisCallbackWithResult;
import cc.lee.redis.client.RedisClient;
import cc.lee.test.cli.spring.BaseTest;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.List;

public class SimpleJedisFactoryBeanTest extends BaseTest {
    @Resource
    private RedisClient redis;

    @Test
    public void testFactoryBean() {
        redis.execute(new JedisCallback() {
            public Void callback(Jedis jedis) {
                Transaction transaction = jedis.multi();
                transaction.set("tran", "事务1");
                transaction.set("tran2", "事务2");
                List<Object> result = transaction.exec();
                for (Object o : result) {
                    System.out.println((String) o);
                }
                return null;
            }
        });

        System.out.println(redis.get("tran"));
        System.out.println(redis.get("tran2"));


        redis.execute(new JedisCallbackWithResult() {
            @Override
            protected void callbackWithoutResult(Jedis jedis) {
                Transaction transaction = jedis.multi();
                transaction.set("tran3", "事务3");
                transaction.set("tran4", "事务4");
                List<Object> result = transaction.exec();
                for (Object o : result) {
                    System.out.println((String) o);
                }
            }
        });

        System.out.println(redis.get("tran3"));
        System.out.println(redis.get("tran4"));
    }
}
