package com.yash.dev.external.client;

import com.yash.dev.external.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yashwanthanands
 */

@FeignClient(name= "PAYMENT-SERVICE/api") //application name of the product service and Request mapping path
public interface PaymentService {

    @PostMapping("/payment/execute")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

}
