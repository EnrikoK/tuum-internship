package com.tuum.demo.DTO;

import com.tuum.demo.utils.enums.TransactionDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {
    private long accountId;
    private long transactionId;
    private double amount;
    private String currency;
    private String direction;
    private String description;
    private double balance;
}
