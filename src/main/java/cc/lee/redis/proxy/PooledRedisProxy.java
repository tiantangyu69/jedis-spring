package cc.lee.redis.proxy;

import cc.lee.redis.PooledRedisClient;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AccessFlag;

import java.util.List;

/**
 * Created by lizhitao on 16-3-7.
 */
public class PooledRedisProxy extends AbstractProxy<PooledRedisClient> {

    @Override
    protected void implementAbstract(CtClass newClass, CtMethod abstractMethod, List<CtClass> definedClasses) throws Exception {
        CtMethod implementMethod = CtNewMethod.copy(abstractMethod, newClass, null);
        newClass.addMethod(implementMethod);

        implementMethod.setModifiers(implementMethod.getModifiers() & ~AccessFlag.ABSTRACT);

        String body = join("{",
                "redis.clients.jedis.Jedis jedis = getResource();",
                "Throwable lastError = null;",
                "try {",
                "    return jedis.", abstractMethod.getName(), "($$);",
                "} catch (Throwable error) {",
                "    lastError = error;",
                "    throw new java.lang.RuntimeException(lastError);",
                "} finally {",
                "    returnResource(jedis);",
                "}",
                "}");

        implementMethod.setBody(body);
    }
}
