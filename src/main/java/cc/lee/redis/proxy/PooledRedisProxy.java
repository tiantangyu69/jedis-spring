package cc.lee.redis.proxy;

import cc.lee.redis.JredisCommands;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AccessFlag;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by lizhitao on 16-3-7.
 */
public class PooledRedisProxy extends AbstractProxy<JredisCommands> {
    private JedisPool jedisPool = new JedisPool();

    @Override
    protected void implementAbstract(CtClass newClass, CtMethod abstractMethod, List<CtClass> definedClasses) throws Exception {
        CtMethod implementMethod = CtNewMethod.copy(abstractMethod,
                newClass, null);
        newClass.addMethod(implementMethod);

        implementMethod.setModifiers(implementMethod.getModifiers() & ~AccessFlag.ABSTRACT);

        String body = join("{",
                "com.jd.cachecloud.driver.jedis.ShardNode node = getShardInfo($1);",
                "redis.clients.jedis.Jedis shardedJedis = borrowFromPool(node);",
                "Throwable lastError = null;",
                "try {",
                "    return shardedJedis.", abstractMethod.getName(), "($$);",
                "} catch (Throwable error) {",
                "    lastError = error;",
                "    throw new com.jd.cachecloud.driver.jedis.ShardedJedisException(node, lastError);",
                "} finally {",
                "    returnToPool(node, shardedJedis, lastError);",
                "}",
                "}");

        implementMethod.setBody(body);
    }
}
