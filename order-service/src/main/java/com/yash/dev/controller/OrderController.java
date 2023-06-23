package com.yash.dev.controller;

import com.yash.dev.model.OrderRequest;
import com.yash.dev.model.OrderResponse;
import com.yash.dev.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yashwanthanands
 */


@RestController
@RequestMapping("/api")
@Log4j2
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orService) {
        orderService=orService;
    }

    @PostMapping("/order/placeorder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id: {} ",orderId);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

}
