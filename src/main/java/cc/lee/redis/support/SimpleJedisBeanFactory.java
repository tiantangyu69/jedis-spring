package cc.lee.redis.support;

import cc.lee.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;

public class SimpleJedisBeanFactory {
	private Integer port;
	private String host;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	protected Jedis create() {
		if (null == this.port)
			this.port = JedisConfig.DEFAULT_PORT;
		if (null == this.host)
			this.host = JedisConfig.DEFAULT_HOST;
		return new Jedis(host, port);
	}
}
