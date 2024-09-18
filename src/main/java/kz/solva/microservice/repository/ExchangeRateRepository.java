package kz.solva.microservice.repository;

import kz.solva.microservice.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findTopByFromCurrencyAndToCurrencyOrderByTimestampDesc(String fromCurrency, String toCurrency);
}
