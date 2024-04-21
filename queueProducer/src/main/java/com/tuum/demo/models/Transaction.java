package com.tuum.demo.models;

import com.tuum.demo.utils.enums.Currency;
import com.tuum.demo.utils.enums.TransactionDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private long transactionId;
    private long accountId;
    private long amount;
    private Currency currency;
    private TransactionDirection direction;
    private String description;

}
