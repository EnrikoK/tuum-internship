package com.demo.queueReceiver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDTO {
    private String customerId;
    private String country;
    private List<String> currencies;
}
