package com.fx.fx_api.model;

import java.util.Map;

public class ExchangeApiResponse {
    private boolean success;
    private Map<String, Double> rates;
    
    public boolean isSuccess() { return success; }
    public Map<String, Double> getRates() { return rates; }

}