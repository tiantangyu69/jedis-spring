package cc.lee.redis.config;

/**
 * jedis配置常量
 * 
 * @author lizhtiao
 */
public class JedisConfig {
	private JedisConfig() {
	}

	/**
	 * redis默认监听端口
	 */
	public static final int DEFAULT_PORT = 6379;
	/**
	 * 默认连接的host
	 */
	public static final String DEFAULT_HOST = "localhost";
}
