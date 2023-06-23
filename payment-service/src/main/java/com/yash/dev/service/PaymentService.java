package com.yash.dev.service;

import com.yash.dev.model.PaymentRequest;

/**
 * @author yashwanthanands
 */
public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
