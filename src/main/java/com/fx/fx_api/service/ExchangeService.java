package com.fx.fx_api.service;

import com.fx.fx_api.client.ExchangeApiClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class ExchangeService {

    private final StringRedisTemplate redisTemplate;
    private final ExchangeApiClient exchangeApiClient;

    /**
     * Constructs the ExchangeService with required dependencies.
     *
     * @param redisTemplate     Template for Redis cache operations
     * @param exchangeApiClient Client responsible for fetching rates from the external API
     */
    public ExchangeService(StringRedisTemplate redisTemplate, ExchangeApiClient exchangeApiClient) {
        this.redisTemplate = redisTemplate;
        this.exchangeApiClient = exchangeApiClient;
    }

    /**
     * Looks for the cacheKey in the Redis cache and returns its stored value.
     *
     * @param cacheKey The key to look up in the cache (e.g., "USD:BRL")
     * @return The cached exchange rate as a String, or null if a cache miss occurs
     */
    public String getExchangeRateFromCache(String cacheKey) {
        System.out.println("getExchangeRateFromCache cacheKey: " + cacheKey);
        String cachedRate = redisTemplate.opsForValue().get(cacheKey);

        if (cachedRate != null) {
            System.out.println("cachedRate: " + cachedRate);
        } else {
            System.out.println("Cache miss for cacheKey: " + cacheKey);
        }
        return cachedRate;
    }

    /**
     * Retrieves the exchange rate between two currencies.
     * First attempts to resolve it from the cache; on a cache miss, fetches it from
     * the external API and caches the result for future requests.
     *
     * @param from Origin currency code (e.g., EUR)
     * @param to   Target currency code (e.g., USD)
     * @return The exchange rate as a String
     */
    public String getExchangeRate(String from, String to) {
        System.out.printf("getExchangeRate from: %s, to: %s%n", from, to);
        String cacheKey = String.format("%s:%s", from, to);

        String cachedRate = getExchangeRateFromCache(cacheKey);
        if (cachedRate != null) {
            return cachedRate;
        }

        Double rate = exchangeApiClient.fetchRate(from, to);
        String rateStr = rate.toString();

        redisTemplate.opsForValue().set(cacheKey, rateStr, Duration.ofHours(1));

        return rateStr;
    }
}