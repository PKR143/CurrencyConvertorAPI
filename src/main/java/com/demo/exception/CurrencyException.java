package com.demo.exception;

import lombok.Setter;

@Setter
public class CurrencyException extends RuntimeException{
    private String status;
    public CurrencyException(String s){
        super(s);
        status = s;
    }
}
