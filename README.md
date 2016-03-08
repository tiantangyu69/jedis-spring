#Jedis Spring客户端

##PooledRedisFactoryBean
###定义RedisClient接口
###使用javassist动态代理JedisPool

##PooledSharedJedisFactoryBean
###定义SharedRedisClient接口
###使用javassist动态代理SharedJedisPool

##MasterSlaveFactoryBean
###定义MasterSlaveRedisClient接口
###使用javassist动态代理MasterSlaveRedisClient

##PooledRedis和MasterSlaveRedis增加对事务的支持
###通过回调Jedis原生API执行事务


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"
       default-autowire="byName">

    <!-- 采用注释的方式配置bean -->
    <context:annotation-config/>

    <!-- 配置要扫描的包 -->
    <context:component-scan base-package="cc.lee"/>

    <context:property-placeholder location="classpath:redis.properties"/>

    <bean id="redis" class="cc.lee.redis.spring.PooledRedisClientFactoryBean">
        <property name="addr" value="localhost:6379"/>
        <property name="maxIdle" value="50"/>
        <property name="maxTotal" value="300"/>
        <property name="minIdle" value="10"/>
        <property name="testOnBorrow" value="true"/>
        <property name="db" value="3"/>
    </bean>

    <bean id="shardedRedis" class="cc.lee.redis.spring.PooledShardedRedisClientFactoryBean">
        <property name="shardInfos">
            <list>
                <bean class="redis.clients.jedis.JedisShardInfo">
                    <constructor-arg name="host" value="localhost"/>
                    <constructor-arg name="port" value="6379"/>
                    <constructor-arg name="name" value="sharded-1"/>
                </bean>
                <bean class="redis.clients.jedis.JedisShardInfo">
                    <constructor-arg name="host" value="localhost"/>
                    <constructor-arg name="port" value="6380"/>
                    <constructor-arg name="name" value="sharded-2"/>
                </bean>
            </list>
        </property>
        <property name="maxIdle" value="50"/>
        <property name="maxTotal" value="300"/>
        <property name="minIdle" value="10"/>
        <property name="testOnBorrow" value="true"/>
    </bean>

    <bean id="masterSlaveRedis" class="cc.lee.redis.spring.PooledMasterSlaveRedisClientFactoryBean">
        <property name="shardInfos">
            <list>
                <bean class="redis.clients.jedis.JedisShardInfo">
                    <constructor-arg name="host" value="localhost"/>
                    <constructor-arg name="port" value="6379"/>
                    <constructor-arg name="name" value="master"/>
                </bean>
                <bean class="redis.clients.jedis.JedisShardInfo">
                    <constructor-arg name="host" value="localhost"/>
                    <constructor-arg name="port" value="6380"/>
                    <constructor-arg name="name" value="slave"/>
                </bean>
            </list>
        </property>
        <property name="maxIdle" value="50"/>
        <property name="maxTotal" value="300"/>
        <property name="minIdle" value="10"/>
        <property name="testOnBorrow" value="true"/>
        <property name="db" value="3"/>
    </bean>

</beans>  
```


```java
package cc.lee.test.cli.spring.jedis;

import cc.lee.redis.JedisCallback;
import cc.lee.redis.JedisCallbackWithResult;
import cc.lee.redis.client.RedisMasterSlaveClient;
import cc.lee.test.cli.spring.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;

@ContextConfiguration(locations = { "classpath:/spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
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

        masterSlaveRedis.execute(new JedisCallbackWithResult() {
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
```
```java
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
```

```java
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
```
