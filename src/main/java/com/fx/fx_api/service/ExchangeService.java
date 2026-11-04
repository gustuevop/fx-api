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

    public String getExchangeRate(String from, String to, String date) {
        String cacheKey = String.format("fx:%s:%s:%s", from.toUpperCase(), to.toUpperCase(), date);
        String cachedRate = redisTemplate.opsForValue().get(cacheKey);

        if (cachedRate != null) {
            System.out.println(String.format("Fx rate encountered on redis for %s to %s -> ",
                    from.toUpperCase(), to.toUpperCase()) + cachedRate);
            return cachedRate;
        }

        System.out.println("Cache miss, starting new request to fx API.");
        //value = getFxRateFromAPI(); just simulation for now
        String fetchedRate = "5.45";
        System.out.println("New rate found, storing on Redis: " + fetchedRate);
        redisTemplate.opsForValue().set(cacheKey, fetchedRate, Duration.ofMinutes(10));

        return fetchedRate;
    }
}
