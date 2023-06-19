package com.yash.dev.service;

import com.yash.dev.model.OrderRequest;
import com.yash.dev.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yashwanthanands
 */

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orRepository) {
        orderRepository=orRepository;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save the data with Status Order Created
        //Product Service -> Block Products (Reduce the Quantity)
        //Payment Service -> Complete the Payment -> If Success-> COMPLETE OR CANCEL for failure
        return 0;
    }
}
