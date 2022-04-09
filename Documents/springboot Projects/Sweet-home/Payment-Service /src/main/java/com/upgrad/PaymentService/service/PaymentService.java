package com.upgrad.PaymentService.service;

import com.upgrad.PaymentService.entity.Payment;

public interface PaymentService {

    Payment getPaymentById(int paymentId) throws Exception;
}
