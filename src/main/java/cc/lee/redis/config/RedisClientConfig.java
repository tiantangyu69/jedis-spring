package cc.lee.redis.config;

import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis配置常量
 *
 * @author lizhtiao
 */
public class RedisClientConfig {
    private JedisPoolConfig config = new JedisPoolConfig();
    private String addr;
    private Integer db;
    private String password;

    public JedisPoolConfig getJedisPoolConfig() {
        return this.config;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getDb() {
        return db;
    }

    public void setDb(Integer db) {
        this.db = db;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }
}
