package com.bee.product.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

@Service
public class CacheService {

   private Cache<String, Object> caffeineCache;

    public CacheService(Cache<String, Object> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    public void loadIntoCache(String key, Long value) {
        caffeineCache.put(key, value);
    }

    public Long getFromCache(String key) {
        return (Long) caffeineCache.getIfPresent(key);
    }

    public ConcurrentMap<String, Object> getContent(){
        return caffeineCache.asMap();
    }

}