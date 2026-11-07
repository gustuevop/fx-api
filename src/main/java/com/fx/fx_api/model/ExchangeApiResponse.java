package com.fx.fx_api.model;

import java.util.Map;

/**
 * Data Transfer Object (DTO) representing the response structure
 * returned by the external Exchange Rates API.
 */
public class ExchangeApiResponse {

    private boolean success;
    private Map<String, Double> rates;

    public boolean isSuccess() { return success; }
    public Map<String, Double> getRates() { return rates; }
}