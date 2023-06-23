package com.yash.dev.service;

import com.yash.dev.model.OrderRequest;
import com.yash.dev.model.OrderResponse;

/**
 * @author yashwanthanands
 */
public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
