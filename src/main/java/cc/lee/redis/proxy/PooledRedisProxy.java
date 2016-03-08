package cc.lee.redis.proxy;

import cc.lee.redis.client.PooledRedisClient;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AccessFlag;

import java.util.List;

/**
 * Created by lizhitao on 16-3-7.
 * javassist代理PooledRedisClient
 */
public class PooledRedisProxy extends AbstractProxy<PooledRedisClient> {

    /**
     * 代理对象，并使用javassist实现对象的方法
     * @param newClass
     * @param abstractMethod
     * @param definedClasses
     * @throws Exception
     */
    @Override
    protected void implementAbstract(CtClass newClass, CtMethod abstractMethod, List<CtClass> definedClasses) throws Exception {
        CtMethod implementMethod = CtNewMethod.copy(abstractMethod, newClass, null);
        newClass.addMethod(implementMethod);

        implementMethod.setModifiers(implementMethod.getModifiers() & ~AccessFlag.ABSTRACT);

        String body = join("{",
                "redis.clients.jedis.Jedis jedis = null;",
                "try {",
                "jedis = getResource();",
                "    return jedis.", abstractMethod.getName(), "($$);",
                "} catch (Exception e) {",
                "    throw new java.lang.RuntimeException(e);",
                "} finally {",
                "    if (null != jedis) jedis.close();",
                "}",
                "}");

        implementMethod.setBody(body);
    }
}
