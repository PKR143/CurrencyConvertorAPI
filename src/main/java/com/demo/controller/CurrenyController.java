package com.demo.controller;

import com.demo.dto.*;
import com.demo.service.CurrencyService;
import com.demo.service.CurrencyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrenyController {

    @Autowired
    CurrencyService service;

    private static final Logger logger = LoggerFactory.getLogger(CurrenyController.class);


    @GetMapping("/{amount}")
    public ResponseEntity<CurrencyResponse> formatCurrency(@PathVariable Long amount)throws NumberFormatException{
        CurrencyRequest req = new CurrencyRequest();
        req.setAmount(amount);
        return service.format(req);
    }

    @GetMapping("/getAllCurrencies")
    public ResponseEntity<ApiResponse> getAllCurrencies(){
        return service.getAllCurrencies();
    }

    @GetMapping("/search/{code}")
    public ResponseEntity<List<DataInfo>> search(@PathVariable String code){
        return service.search(code);
    }

    @GetMapping("/convert")
    public ResponseEntity<CurrencyConvertResponse> convert(@RequestParam String code, @RequestParam Double amount){
        return  service.convert(code,amount);
    }

}
