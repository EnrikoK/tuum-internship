package com.demo.queueReceiver.dto;

import com.demo.queueReceiver.utils.enums.TransactionDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {

    private long accountId;
    private double amount;
    private String currency;
    private String direction;
    private String description;
}
