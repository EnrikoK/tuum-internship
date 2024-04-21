package com.tuum.demo.DTO;


import com.tuum.demo.utils.enums.Currency;
import com.tuum.demo.utils.enums.TransactionDirection;
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
