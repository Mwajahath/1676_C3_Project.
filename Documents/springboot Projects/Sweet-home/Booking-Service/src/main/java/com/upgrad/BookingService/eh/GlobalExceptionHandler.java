package com.upgrad.BookingService.eh;

import com.upgrad.BookingService.dto.CustomResponse;
import com.upgrad.BookingService.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


        @ExceptionHandler({CustomException.class})
        public ResponseEntity<CustomResponse> handleCustomException(Exception ex) {
            CustomResponse exceptionResponse = new CustomResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<CustomResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
}

