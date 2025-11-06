package com.anderson.invest.exceptions;

public class RabbitMQException extends RuntimeException{

    public RabbitMQException(String msg){
        super(msg);
    }
}
