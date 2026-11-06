package com.fx.fx_api.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {
    private final StringRedisTemplate redisTemplate;

    public ExchangeService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Looks for cacheKey in cache and return its value;
     * @param cacheKey key to be found in cache;
     * @return null when there's no value stored on cacheKey in cache
     */
    public String getExchangeRateFromCache(String cacheKey) {
        System.out.println("getExchangeRateFromCache cacheKey: " + cacheKey);
        String cachedRate = redisTemplate.opsForValue().get(cacheKey);

        if (cachedRate != null) {
            System.out.println("cachedRate: " + cachedRate);
        } else {
            System.out.println("Cache miss, starting new request to fx API.");
        }
        return cachedRate;
    }
}
