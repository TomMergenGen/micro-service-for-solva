package kz.solva.microservice.service;

import kz.solva.microservice.entity.Transaction;
import kz.solva.microservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyExchangeService currencyExchangeService;

    private double monthlyLimit = 1000.0;  // Дефолтный лимит в USD
    private LocalDateTime limitSetDate = LocalDateTime.now().withDayOfMonth(1);  // Дата установки лимита

    public TransactionService(TransactionRepository transactionRepository, CurrencyExchangeService currencyExchangeService) {
        this.transactionRepository = transactionRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    public Transaction recordTransaction(String category, double amount, String currency) {
        //Получаем курс валюты как строку
        String exchangeRateStr = (String)currencyExchangeService.getExchangeRate(currency, "USD").get("5. Exchange rate");

        //Преобраззуем строку в double
        double exchangeRate = Double.parseDouble(exchangeRateStr);

        //Рассчитываем сумму в USD
        double amountInUSD = amount*exchangeRate;

        // Считаем общий расход за месяц
        double totalExpensesInUSD = getTotalExpensesForMonth();

        // Проверяем, превышен ли лимит
        boolean limitExceeded = (totalExpensesInUSD + amountInUSD) > monthlyLimit;

        // Создаем новую транзакцию
        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setAmountInUSD(amountInUSD);
        transaction.setLimitExceeded(limitExceeded);
        transaction.setTransactionDate(LocalDateTime.now());

        // Сохраняем транзакцию
        transactionRepository.save(transaction);

        return transaction;
    }

    // Метод для подсчета всех расходов за текущий месяц
    public double getTotalExpensesForMonth() {
        List<Transaction> transactions = transactionRepository.findAll();  // Здесь можно добавить фильтрацию по дате
        return transactions.stream()
                .mapToDouble(Transaction::getAmountInUSD)
                .sum();
    }

    // Метод для установки нового лимита
    public void setMonthlyLimit(double newLimit) {
        monthlyLimit = newLimit;
        limitSetDate = LocalDateTime.now();
    }
}
