package com.yash.dev.controller;

import com.yash.dev.model.PaymentRequest;
import com.yash.dev.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yashwanthanands
 */

@RestController
@RequestMapping("/api")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService payService) {
        paymentService=payService;
    }


    @PostMapping("/payment/execute")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest), HttpStatus.OK);
    }

}
