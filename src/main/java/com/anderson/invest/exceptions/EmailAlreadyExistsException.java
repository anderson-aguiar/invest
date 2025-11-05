package com.anderson.invest.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String msg){
        super(msg);
    }
}
