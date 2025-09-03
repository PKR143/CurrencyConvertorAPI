package com.demo.service;

import com.demo.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CurrencyService {
    ResponseEntity<CurrencyResponse> format(CurrencyRequest req);

    ResponseEntity<ApiResponse> getAllCurrencies();

    ResponseEntity<List<DataInfo>> search(String code);

     ResponseEntity<CurrencyConvertResponse> convert(String code, Double amount);

}
