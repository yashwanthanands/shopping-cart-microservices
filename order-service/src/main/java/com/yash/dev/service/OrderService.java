package com.yash.dev.service;

import com.yash.dev.model.OrderRequest;

/**
 * @author yashwanthanands
 */
public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
