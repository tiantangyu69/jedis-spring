package cc.lee.redis.proxy;

import cc.lee.redis.client.PooledRedisMasterSlaveClient;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AccessFlag;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lizhitao on 16-3-8.
 */
public class PooledMasterSlaveRedisProxy extends AbstractProxy<PooledRedisMasterSlaveClient> {
    private final List<String> masterFirstCommands;

    public PooledMasterSlaveRedisProxy() {
        this.masterFirstCommands = Arrays.asList(
                "get",
                "hget",
                "hmget",
                "getbit",
                "getrange",
                "hlen",
                "hkeys",
                "hvals",
                "hgetAll",
                "llen",
                "lrange",
                "lindex",
                "smembers",
                "scard",
                "sismember",
                "srandmember",
                "zrange",
                "zrank",
                "zrevrank",
                "zrevrange",
                "zrangeWithScores",
                "zrevrangeWithScores",
                "zcard",
                "zscore",
                "sort",
                "zcount",
                "zrangeByScore",
                "zrevrangeByScore",
                "zrangeByScoreWithScores",
                "zrevrangeByScoreWithScores"
        );
    }

    @Override
    protected void implementAbstract(CtClass newClass, CtMethod abstractMethod, List<CtClass> definedClasses) throws Exception {
        CtMethod implementMethod = CtNewMethod.copy(abstractMethod, newClass, null);
        newClass.addMethod(implementMethod);

        implementMethod.setModifiers(implementMethod.getModifiers() & ~AccessFlag.ABSTRACT);
        String methodName = abstractMethod.getName();

        String body = null;
        if (masterFirstCommands.contains(methodName)) {
            body = join(
                    "{try {",
                    "    return this.master.", methodName, "($$);",
                    "} catch (RuntimeException e) {",
                    "    if (slave == null) { throw e;}",
                    "    logger.debug(\"failed to execute {} on master, try on slave.\", \"",
                    methodName, "\");",
                    "    return this.slave.", methodName, "($$);",
                    "}}"
            );
        } else {
            body = join(
                    "{return this.master.", methodName, "($$);}"
            );
        }

        implementMethod.setBody(body);
    }
}
