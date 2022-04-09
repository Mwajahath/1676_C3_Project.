package com.upgrad.BookingService.service;

import com.upgrad.BookingService.dto.BookingDTO;
import com.upgrad.BookingService.dto.PaymentDTO;
import com.upgrad.BookingService.entity.Booking;

public interface BookingService{
    Booking bookingDetails(BookingDTO bookingRequest) throws Exception;
    Booking doPayment(PaymentDTO paymentDetails) throws Exception;
}
