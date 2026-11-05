package com.fx.fx_api.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ExchangeService {
    private final StringRedisTemplate redisTemplate;

    public ExchangeService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getExchangeRate(String from, String to) {
        String cacheKey = String.format("fx:%s:%s", from.toUpperCase(), to.toUpperCase());
        String cachedRate = redisTemplate.opsForValue().get(cacheKey);

        if (cachedRate != null) {
            System.out.println(String.format("Fx rate encountered on redis for %s to %s -> ",
                    from.toUpperCase(), to.toUpperCase()) + cachedRate);
            return cachedRate;
        }

        System.out.println("Cache miss, starting new request to fx API.");
        return getFxRateFromAPI(from.toUpperCase(), to.toUpperCase());
    }

    private String getFxRateFromAPI(String from, String to) {

    }
}
