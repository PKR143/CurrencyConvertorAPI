package com.demo.dto;

import lombok.Data;

@Data
public class CurrencyConvertResponse {
    private String from;
    private Double amount;

    private String to;
    private Double convertedAmount;

}
