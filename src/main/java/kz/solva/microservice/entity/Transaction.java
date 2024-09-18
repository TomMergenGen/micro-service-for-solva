package kz.solva.microservice.entity;

import jakarta.persistence.*;
import  lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private double amount;
    private String currency;
    private double amountInUSD;
    private boolean limitExceeded;
    private LocalDateTime transactionDate;
}
