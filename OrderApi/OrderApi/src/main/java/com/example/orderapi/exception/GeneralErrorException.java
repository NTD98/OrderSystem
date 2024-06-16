package com.example.orderapi.exception;

public class GeneralErrorException extends RuntimeException{
    public GeneralErrorException(String message){
        super(message);
    }
}
