package kz.solva.microservice.service;

import jakarta.websocket.server.ServerEndpoint;
import kz.solva.microservice.entity.ExchangeRate;
import kz.solva.microservice.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyExchangeService {

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    @Value("${alpha.vantage.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;

    public CurrencyExchangeService(RestTemplate restTemplate, ExchangeRateRepository exchangeRateRepository) {
        this.restTemplate = restTemplate;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public Map<String, Object> getExchangeRate(String fromCurrency, String toCurrency) {
        // Сборка URL для вызова API
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("function", "CURRENCY_EXCHANGE_RATE")
                .queryParam("from_currency", fromCurrency)
                .queryParam("to_currency", toCurrency)
                .queryParam("apikey", apiKey)
                .toUriString();

        // Вызов API и получение ответа
        Map<String, Object> response = restTemplate.getForObject(url, HashMap.class);

        // Проверяем, что ответ содержит нужные данные
        if (response != null && response.containsKey("Realtime Currency Exchange Rate")) {
            Map<String, String> rateData = (Map<String, String>) response.get("Realtime Currency Exchange Rate");

            // Форматированный вывод
            System.out.println("Exchange Rate Details:");
            System.out.println("1. From Currency Code: " + rateData.get("1. From_Currency Code"));
            System.out.println("2. From Currency Name: " + rateData.get("2. From_Currency Name"));
            System.out.println("3. To Currency Code: " + rateData.get("3. To_Currency Code"));
            System.out.println("4. To Currency Name: " + rateData.get("4. To_Currency Name"));
            System.out.println("5. Exchange Rate: " + rateData.get("5. Exchange Rate"));
            System.out.println("6. Last Refreshed: " + rateData.get("6. Last Refreshed"));
            System.out.println("7. Time Zone: " + rateData.get("7. Time Zone"));
            System.out.println("8. Bid Price: " + rateData.get("8. Bid Price"));
            System.out.println("9. Ask Price: " + rateData.get("9. Ask Price"));

        } else {
            System.out.println("Error: Could not retrieve currency exchange rate.");
        }

        return response;

    }
}
