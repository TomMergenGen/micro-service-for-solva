package kz.solva.microservice.controller;

import kz.solva.microservice.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping("/exchange-rate")
    public Map<String, Object> getExchangeRate(@RequestParam String from, @RequestParam String to){
        return currencyExchangeService.getExchangeRate(from, to);
    }
}
