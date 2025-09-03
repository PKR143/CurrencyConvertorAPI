package com.demo.service;

import com.demo.dto.*;
import com.demo.exception.CurrencyException;
import com.demo.model.CurrencyRecords;
import com.demo.repo.CurrencyRepository;
import com.demo.util.CountryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Autowired
    CurrencyRepository repo;

    @Value("${currency.url}")
    private String url;

    private RestTemplate rest = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Override
    public ResponseEntity<CurrencyResponse> format(CurrencyRequest req) {
        logger.info("request to format currency: {}",req);
        NumberFormat formatter;
        CountryEnum[] countryArr = CountryEnum.values();
        CurrencyResponse response = new CurrencyResponse();
        try {
            for (CountryEnum c : countryArr) {
                Locale locale;
                switch (c) {
                    case IN:
                        locale = new Locale("en", "IN");
                        break;
                    case US:
                        locale = Locale.US;
                        break;
                    case UK:
                        locale = Locale.UK;
                        break;
                    case CHINA:
                        locale = Locale.CHINA;
                        break;
                    case JAPAN:
                        locale = Locale.JAPAN;
                        break;
                    case CANADA:
                        locale = Locale.CANADA;
                        break;
                    case FRANCE:
                        locale = Locale.FRANCE;
                        break;
                    default:
                        locale = Locale.US;
                }
                formatter = NumberFormat.getCurrencyInstance(locale);
                String currency = formatter.format(req.getAmount());
                switch (c) {
                    case IN:
                        response.setIN(currency);
                        break;
                    case US:
                        response.setUS(currency);
                        break;
                    case UK:
                        response.setUK(currency);
                        break;
                    case CHINA:
                        response.setCHINA(currency);
                        break;
                    case JAPAN:
                        response.setJAPAN(currency);
                        break;
                    case CANADA:
                        response.setCANADA(currency);
                        break;
                    case FRANCE:
                        response.setFRANCE(currency);
                        break;
                }

            }
            CurrencyRecords records = mapToRecords(req, response);
            repo.save(records);
            logger.info("response: {}", response);
        }catch(Exception e){
            logger.info("something went wrong!! @format()");
            throw new CurrencyException(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getAllCurrencies() {
        logger.info("request for getting all currencies");
        try {
            ApiResponse apiResponse = rest.getForObject(url, ApiResponse.class);
            logger.info("response sent for all currencies");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch(Exception e){
            logger.info("something went wrong @ getAllCurrencies()");
            throw new CurrencyException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<DataInfo>> search(String code) {
        logger.info("request for searching via country code: {}", code);
        List<DataInfo> list = new ArrayList<>();
        try {
            ApiResponse apiResponse = rest.getForObject(url, ApiResponse.class);
            assert apiResponse != null: "api response is null";
            Map<String, DataInfo> countryMap = apiResponse.getData();
            list = countryMap.values().stream().toList();
            list = list.stream().filter(l -> code.equalsIgnoreCase(l.getCode())).toList();
        }catch(Exception e){
            throw new CurrencyException(e.getMessage());
        }
        logger.info("response for search with code {} is {}",code,list);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CurrencyConvertResponse> convert(String code, Double amount){

        try {
            CurrencyConvertResponse response = new CurrencyConvertResponse();
            response.setFrom("INR");
            response.setAmount(amount);
            double inr = 88.0319657337;
            ResponseEntity<List<DataInfo>> searchRes = search(code);
            Double countryValue = searchRes.getBody().get(0).getValue();
            double usd = 1/inr;
            amount *= usd;
            Double result = amount * countryValue;
            response.setTo(code);
            response.setConvertedAmount(result);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(NullPointerException e){
            logger.info("something went wrong @convert()");
            throw new CurrencyException(e.getMessage());
        }
    }

    public CurrencyRecords mapToRecords(CurrencyRequest amount, CurrencyResponse res){

        CurrencyRecords records = new CurrencyRecords();
        records.setAmount(amount.getAmount());
        records.setIN(res.getIN());
        records.setUS(res.getUS());
        records.setUK(res.getUK());
        records.setCHINA(res.getCHINA());
        records.setFRANCE(res.getFRANCE());
        records.setJAPAN(res.getJAPAN());
        records.setCANADA(res.getCANADA());
        records.setDate(LocalDate.now());
        return records;

    }
}
