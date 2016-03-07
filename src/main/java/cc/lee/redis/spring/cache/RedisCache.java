package cc.lee.redis.spring.cache;

import org.springframework.cache.Cache;

/**
 * Created by lizhitao on 16-3-7.
 */
public class RedisCache<T> implements Cache {
    public String getName() {
        return null;
    }

    public Object getNativeCache() {
        return null;
    }

    public ValueWrapper get(Object o) {
        return null;
    }

    public <T> T get(Object o, Class<T> aClass) {
        return null;
    }

    public void put(Object o, Object o1) {

    }

    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    public void evict(Object o) {

    }

    public void clear() {

    }
}
