package com.upgrad.BookingService.controller;

import com.upgrad.BookingService.dto.BookingDTO;
import com.upgrad.BookingService.dto.PaymentDTO;
import com.upgrad.BookingService.entity.Booking;
import com.upgrad.BookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping(value = "/BookingService/v1")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @Autowired
    private ModelMapper modelMapper;

    //localhost:8081/BookingService/v1/booking/{bookingId}/transaction
    @PostMapping("/booking/{bookingId}/transaction")
    public ResponseEntity<Booking> doPayment( @RequestBody PaymentDTO paymentDetails) throws Exception {
        Booking bookingInfo = bookingService.doPayment(paymentDetails);
        return new ResponseEntity<Booking>(bookingInfo, HttpStatus.CREATED);
    }

    //localhost:8081/BookingService/v1/booking
    @PostMapping("/booking")
    public ResponseEntity<Booking> bookingDetails(@RequestBody BookingDTO bookingRequest) throws Exception {
        Booking bookingInfo =  this.bookingService.bookingDetails(bookingRequest);
        return new ResponseEntity<Booking>(bookingInfo, HttpStatus.CREATED);

    }
}
