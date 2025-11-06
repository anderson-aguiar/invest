package com.anderson.invest.exceptions;

public class CustomAccessDeniedException extends RuntimeException{

    public CustomAccessDeniedException(String msg){
        super(msg);
    }
}
