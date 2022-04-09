package com.upgrad.PaymentService.service;

import com.upgrad.PaymentService.dao.PaymentDao;
import com.upgrad.PaymentService.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentDao paymentDetailDao;


    @Autowired
    public PaymentServiceImpl(PaymentDao paymentDetailDao) {
        super();
        this.paymentDetailDao = paymentDetailDao;
    }

    public int  makePayment(Payment paymentRequest) {
        return this.paymentDetailDao.save(paymentRequest).getTransactionId();
    }

    @Override
    public Payment getPaymentById(int paymentId) throws Exception {

            Optional<Payment> paymentDetailDaoOp=  this.paymentDetailDao.findById(paymentId);

            if(paymentDetailDaoOp.isPresent()) {
                return paymentDetailDaoOp.get();
            }else {
                throw new Exception("Payment Id Not Found");
            }
        }


}


