package com.yash.dev.external.client;

import com.yash.dev.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yashwanthanands
 */

@CircuitBreaker(name="external", fallbackMethod = "fallback")
@FeignClient(name= "PRODUCT-SERVICE/api") //application name of the product service and Request mapping path
public interface ProductService {

    @PutMapping("/product/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity);

    default void fallback(Exception e) {
        throw new CustomException("PRODUCT SERVICE IS NOT AVAILABLE","UNAVAILABLE",500);
    }

}
