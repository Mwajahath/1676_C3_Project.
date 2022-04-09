package com.upgrad.PaymentService.controller;

import com.upgrad.PaymentService.entity.Payment;
import com.upgrad.PaymentService.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentServiceImpl paymentService;


    //localhost:8083/transaction
    @PostMapping("/transaction")
    public ResponseEntity<Integer> makePayment(@RequestBody Payment paymentDetails) {
        int paymentId =  this.paymentService.makePayment(paymentDetails);

        return new ResponseEntity<Integer>(paymentId, HttpStatus.CREATED);
    }


    //localhost:8083/transaction/{transactionId}
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int transactionId) throws Exception {
        Payment paymentDetails =  this.paymentService.getPaymentById(transactionId);

        return new ResponseEntity<Payment>(paymentDetails, HttpStatus.OK);
    }
}

