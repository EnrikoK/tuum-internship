package com.demo.queueReceiver.models;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private long accountId;
    private String customerId;
    private List<Balance> balances;
}
