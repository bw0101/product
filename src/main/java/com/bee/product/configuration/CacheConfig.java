package com.bee.product.configuration;

import com.bee.product.service.MessageSender;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, Object> caffeineCache(MessageSender messageSender) {
        return Caffeine.newBuilder()
                .maximumSize(300)
                .removalListener((RemovalListener<String, Object>) (key, value, cause) -> {
                    if (cause.wasEvicted()) {
                        String evictionMessage = "#### Cache: Evicted key: " + key + ", Value: " + value;
                        messageSender.sendMessage(evictionMessage);
                    }
                })
                .build();
    }
}