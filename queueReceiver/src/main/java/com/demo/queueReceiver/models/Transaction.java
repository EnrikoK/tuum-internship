package com.demo.queueReceiver.models;

import com.demo.queueReceiver.utils.enums.Currency;
import com.demo.queueReceiver.utils.enums.TransactionDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private long transactionId;
    private long accountId;
    private double amount;
    private String currency;
    private String direction;
    private String description;
}
