package com.tuum.demo.models;

import com.tuum.demo.utils.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private double amount;
    private Currency currency;

}
