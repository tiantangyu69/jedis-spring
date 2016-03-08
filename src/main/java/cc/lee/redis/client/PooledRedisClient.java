package cc.lee.redis.client;

import cc.lee.redis.JedisCallback;
import cc.lee.redis.JedisCallbackWithoutResult;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * Created by lizhitao on 16-3-7.
 */
public abstract class PooledRedisClient extends JedisPool implements RedisClient {
	/**
	 * 构造Jedis pool
	 * @param poolConfig commonpool配置
	 * @param host redis host
	 * @param port redis 端口号
	 * @param password redis授权密码
     * @param db 连接的数据库
     */
	public PooledRedisClient(JedisPoolConfig poolConfig, String host, int port,
			String password, int db) {
		super(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, password, db);
	}

	/**
	 * 执行Jedis原生API
	 * @param callback
	 * @param <T>
     * @return
     */
	public <T> T execute(JedisCallback callback){
		return (T) callback.callback(getResource());
	}

    /**
     * 执行Jedis原生API无返回结果
     * @param callbackWithResult
     */
	public void execute(JedisCallbackWithoutResult callbackWithResult){
		callbackWithResult.callback(getResource());
	}

	/**
	 * 销毁连接池
	 */
	public void destory() {
		super.destroy();
	}
}
