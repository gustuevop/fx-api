package com.fx.fx_api.constroller;

import com.fx.fx_api.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fx")
public class ExchangeController {

    private final ExchangeService exchangeService;
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping
    public ResponseEntity<String> consultExchangeRate(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String date) {
        String response = exchangeService.getExchangeRate(from, to, date);
        return ResponseEntity.ok(response);
    }
}
