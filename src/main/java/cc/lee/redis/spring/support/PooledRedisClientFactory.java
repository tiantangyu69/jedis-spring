package cc.lee.redis.spring.support;

import cc.lee.redis.JredisCommands;

/**
 * Created by lizhitao on 16-3-7.
 */
public class PooledRedisClientFactory {
    private String addr;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxTotal;
    private Boolean testOnBorrow;

    JredisCommands create() {
        return null;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
}
