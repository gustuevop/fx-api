package com.fx.fx_api.client;

import com.fx.fx_api.model.ExchangeApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ExchangeApiClient {

    private final RestClient restClient;
    private final String apiKey;

    /**
     * Constructs the ExchangeApiClient injecting the base URL and API key from application properties.
     *
     * @param restClientBuilder Builder provided by Spring to instantiate RestClient
     * @param baseUrl           Base URL of the external exchange rate API
     * @param apiKey            Access key for authenticating with the external API
     */
    public ExchangeApiClient(
            RestClient.Builder restClientBuilder,
            @Value("${exchangerates.api.base-url}") String baseUrl,
            @Value("${exchangerates.api.key}") String apiKey) {

        this.apiKey = apiKey;
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Fetches the exchange rate between two currencies from the external API.
     *
     * @param from Origin currency code (e.g., USD)
     * @param to   Target currency code (e.g., BRL)
     * @return The exchange rate value as a Double
     */
    public Double fetchRate(String from, String to) {
        System.out.printf("Fetching exchange rate from external API for %s -> %s%n", from, to);

        try {
            ExchangeApiResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/latest")
                            .queryParam("access_key", apiKey)
                            .queryParam("base", from)
                            .queryParam("symbols", to)
                            .build())
                    .retrieve()
                    .body(ExchangeApiResponse.class);

            if (response != null && response.isSuccess() && response.getRates() != null) {
                return response.getRates().get(to);
            }

            throw new RuntimeException("Exchange rate not found in the API response.");

        } catch (Exception e) {
            System.err.println("Error communicating with the external API: " + e.getMessage());
            throw new RuntimeException("Failed to communicate with the external exchange service.", e);
        }
    }
}