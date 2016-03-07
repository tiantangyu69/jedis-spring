package cc.lee.redis.spring.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by lizhitao on 16-3-7.
 * redis spring cache支持
 */
public class RedisCacheManager extends AbstractCacheManager {
    private Collection<? extends Cache> caches;

    public void setCaches(List<RedisCache<?>> caches) {
        this.caches = caches;
    }

    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if (null == cache) {
            throw new IllegalArgumentException("No such cache defined:" + name);
        } else {
            return cache;
        }
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return (Collection) (null == this.caches ? Collections.emptyList() : this.caches);
    }
}
