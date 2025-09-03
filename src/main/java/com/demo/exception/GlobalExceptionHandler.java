package com.demo.exception;

import org.hibernate.dialect.HANADialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    private static  final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String,String>> handleNumberFormatException(NumberFormatException e){
        logger.info("NumberFormatException due to : {}",e.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("status",e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CurrencyException.class)
    public ResponseEntity<Map<String,String>> handleCurrencyException(CurrencyException e){
        logger.info("CurrencyException due to: {}",e.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("status",e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> Exception(Exception e){
        logger.info("Exception due to: {}",e.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("status",e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
