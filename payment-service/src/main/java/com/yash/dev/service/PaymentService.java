package com.yash.dev.service;

import com.yash.dev.model.PaymentRequest;
import com.yash.dev.model.PaymentResponse;

/**
 * @author yashwanthanands
 */
public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
