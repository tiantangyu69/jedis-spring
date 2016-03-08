package cc.lee.redis.config;

import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis配置常量
 *
 * @author lizhtiao
 */
public class RedisClientConfig implements Cloneable {
    /**
     * pool配置
     */
    private JedisPoolConfig config = new JedisPoolConfig();
    private String host;
    private Integer port;
    private Integer db;
    private String password;

    public JedisPoolConfig getJedisPoolConfig() {
        return this.config;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public String getPassword() {
        return this.password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RedisClientConfig cloneConfig = (RedisClientConfig) super.clone();
        cloneConfig.config = (JedisPoolConfig) config.clone();
        return cloneConfig;
    }
}
