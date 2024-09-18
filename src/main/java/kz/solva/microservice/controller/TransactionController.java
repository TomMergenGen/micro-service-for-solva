package kz.solva.microservice.controller;

import kz.solva.microservice.entity.Transaction;
import kz.solva.microservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/record")
    public Transaction recordTransaction(@RequestParam String category,
                                         @RequestParam double amount,
                                         @RequestParam String currency){
        return  transactionService.recordTransaction(category,amount,currency);
    }

    @PostMapping("/set-limit")
    public void setLimit(@RequestParam double limit){
        transactionService.setMonthlyLimit(limit);
    }
}
