package cc.lee.redis.proxy;

import cc.lee.redis.PooledRedisClient;
import cc.lee.redis.PooledShardedRedisClient;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AccessFlag;
import redis.clients.jedis.ShardedJedis;

import java.util.List;

/**
 * Created by lizhitao on 16-3-8.
 */
public class PooledShardedRedisProxy extends AbstractProxy<PooledShardedRedisClient> {
    @Override
    protected void implementAbstract(CtClass newClass, CtMethod abstractMethod, List<CtClass> definedClasses) throws Exception {
        CtMethod implementMethod = CtNewMethod.copy(abstractMethod, newClass, null);
        newClass.addMethod(implementMethod);

        implementMethod.setModifiers(implementMethod.getModifiers() & ~AccessFlag.ABSTRACT);

        String body = join("{",
        "redis.clients.jedis.ShardedJedis shardedJedis = null;",
        "try {",
            "shardedJedis = getResource();",
            "return shardedJedis.", abstractMethod.getName(), "($$);",
        "} catch (Exception e) {",
            "throw new java.lang.RuntimeException(e);",
        "} finally {",
            "if (null != shardedJedis) {",
                "shardedJedis.close();",
            "}",
        "}}");

        implementMethod.setBody(body);
    }
}
