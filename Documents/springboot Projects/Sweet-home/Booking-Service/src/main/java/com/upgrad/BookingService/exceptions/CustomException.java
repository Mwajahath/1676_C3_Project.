package com.upgrad.BookingService.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class CustomException extends Exception{
    public CustomException(String message) {
        super(message);
    }
}
